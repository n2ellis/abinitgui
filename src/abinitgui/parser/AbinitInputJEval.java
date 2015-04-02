/*
 AbinitGUI - Created in 2009
 
 Copyright (c) 2009-2015 Flavio Miguel ABREU ARAUJO (abreuaraujo.flavio@gmail.com)
                         Yannick GILLET (yannick.gillet@hotmail.com)

 Université catholique de Louvain, Louvain-la-Neuve, Belgium
 All rights reserved.

 AbinitGUI is free software: you can redistribute it and/or modify
 it under the terms of the GNU General Public License as published by
 the Free Software Foundation, either version 3 of the License, or
 (at your option) any later version.

 AbinitGUI is distributed in the hope that it will be useful,
 but WITHOUT ANY WARRANTY; without even the implied warranty of
 MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 GNU General Public License for more details.

 You should have received a copy of the GNU General Public License
 along with AbinitGUI.  If not, see <http://www.gnu.org/licenses/>.

 For more information on the project, please see
 <http://gui.abinit.org/>.
 */

package abinitgui.parser;

import abivars.AllInputVars;
import abivars.MultipleValue;
import abivars.ValueWithConditions;
import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import abivars.Variable;
import java.util.Map.Entry;
import java.util.logging.Level;
import java.util.logging.Logger;
import net.sourceforge.jeval.EvaluationException;
import net.sourceforge.jeval.Evaluator;

// TODO : Change the way we build things. We should start by filling the table variable : value with the default values
public class AbinitInputJEval 
{
    private String content = null;
    private double Ha_to_eV = 27.21138386;
    private double ev_to_Ha = 1.0/Ha_to_eV;
    private double kb_to_HaK = 8.617343E-5/Ha_to_eV;
    private double tesla_to_au = 4.254383E-6*0.5;
    private double bohr_to_angstrom=0.52917720859;
    private double angstrom_to_bohr=1.0/bohr_to_angstrom;
    private AllInputVars allInputs = null;
    private HashMap<String,ArrayList> map;
    private HashMap<String,String> mapString;
    public static HashMap<String,Double> listOfUnits = null;
    private final String[] units = {"HA","HARTREE","RY","RYDBERG","BOHR","AU", "T","TE", "EV", "ANGSTROM", "ANGSTR", "ANGSTROMS", "K", "A.U.", "(BOHR^2)/HARTREE"};
    private final double[] scaling = {1.0,1.0,0.5,0.5,1.0,1.0,tesla_to_au,tesla_to_au,ev_to_Ha,angstrom_to_bohr,angstrom_to_bohr,angstrom_to_bohr,1.0,1.0,1.0};
    
    public AbinitInputJEval()
    {
        allInputs = new AllInputVars();
        allInputs.loadVars("abinit_vars.yml");
        
        listOfUnits = new HashMap<>();
        
        for(int i = 0; i < units.length; i++)
        {
            listOfUnits.put(units[i],scaling[i]);
        }
        
    }
    
    public AbinitInputMapping readFromFile(String fileName) throws IOException
    {
        content = fileToString(fileName);
        content = content.trim(); // Remove spaces at the beginning and at the end
        content = content.replaceAll(" +"," "); // Replace multiple spaces by 1 space
        
        mapString = tabularize(content, allInputs);
        
        return buildDtset(mapString);
    }
    
    @Override
    public String toString()
    {
        return "Abinit Input : "+mapString;
    }
    
    private HashMap<String, String> tabularize(String content, AllInputVars allInputs) throws InvalidInputFileException
    {
        HashMap<String,String> newMap = new HashMap<>();
        
        String[] splitted = content.split(" ");
        
        String keyword = null;
        StringBuilder sb = new StringBuilder();
        
        for(int i = 0; i < splitted.length; i++)
        {
            String word = splitted[i].toLowerCase();
            
            //System.out.print("Looking for word "+word+"...");
            
            if(isVariable(word))
            {
                if(keyword != null)
                {
                    newMap.put(keyword,sb.toString().trim());
                    sb = new StringBuilder();
                }
                keyword = word;
            }
            else if(isUnit(word) || isNumber(word) || word.contains("*"))
            {
                sb.append(word).append(" ");
            }
            else
            {
                throw new InvalidInputFileException("Unrecognized token : "+word);
            }
            
        }
        
        if(keyword != null)
        {
            newMap.put(keyword, sb.toString().trim());
        }
        
        
        return newMap;
    }
    
    // http://stackoverflow.com/questions/7597485/how-to-check-if-a-string-is-a-number
    public boolean isNumber(String str) {
        Pattern pattern = Pattern.compile("(^-?)(\\d*)(.?)(\\d*$)"); // \\'D'?\\-?\\d* [^/]
        Matcher matcher = pattern.matcher(str);
        boolean isSimpleNumber = matcher.matches();
        pattern = Pattern.compile("(^-?)(\\d*)(.?)(\\d*)[DEFdef]([+-]?)(\\d+)(.?)(\\d*$)");
        matcher = pattern.matcher(str);
        boolean isExponential = matcher.matches();
        pattern = Pattern.compile("(^-?)(\\d*)(.?)(\\d*)/(-?)(\\d+)(.?)(\\d*$)");
        matcher = pattern.matcher(str);
        boolean isFraction = matcher.matches();
        boolean isSQRT = false;
        if(str.startsWith("sqrt(") || str.startsWith("SQRT("))
        {
            isSQRT = isNumber(str.substring(5,str.length()-1));
        }
        else if(str.startsWith("-sqrt(") || str.startsWith("-SQRT("))
        {
            isSQRT = isNumber(str.substring(6,str.length()-1));
        }
        return isSimpleNumber || isFraction || isExponential || isSQRT;
    }
    
    private boolean isUnit(String word)
    {
        return listOfUnits.keySet().contains(word.toUpperCase());
    }
    
    private boolean isVariable(String word)
    {
        word = word.trim();
        Pattern pattern = Pattern.compile("(^[a-zA-Z0-9_]+)(\\d*)([a-zA-Z0-9_]*)(\\d*$)");
        Matcher matcher = pattern.matcher(word);
        boolean isText = matcher.matches();
        pattern = Pattern.compile("");
        matcher = pattern.matcher(word); // TODO: NEVER USED
        boolean isTextWithPlus = word.contains("+");
        boolean isTextWithPoint = word.contains(":");
        boolean isTextWithInter = word.contains("?");
        String curWord = word.replaceAll("\\+","").replaceAll(":", "").replaceAll("\\?","").replaceFirst("(\\d*$)", "");
        boolean isVar = allInputs.getListKeys().contains(curWord);
        return (isText || isTextWithPoint || isTextWithPlus || isTextWithInter) && isVar ;
    }
    
    // Returns a string from a file where the next line are appended.
    // Remove comments
    public String fileToString(String fileName) throws FileNotFoundException, IOException
    {
        BufferedReader br = new BufferedReader(new FileReader(fileName));
        String line;
        StringBuilder sb = new StringBuilder();
        while((line = br.readLine()) != null)
        {
            // Remove comments from line (starting with # or !)
            if(line.contains("#"))
            {
                if(line.startsWith("#"))
                    continue;
                line = line.split("#")[0];
            }
            
            if(line.contains("!"))
            {
                if(line.startsWith("!"))
                    continue;
                line = line.split("!")[0];
            }
            
            sb.append(line);
            sb.append(" ");
        }
        
        return sb.toString();
    }

    private AbinitInputMapping buildDtset(HashMap<String, String> mapString) throws InvalidInputFileException {
        
        AbinitInputMapping allDatasets = new AbinitInputMapping();
        allDatasets.setDatabase(allInputs);
        
        // Find ndtset
        String ndtset_S = mapString.get("ndtset");
        
        if(ndtset_S != null)
        {
            allDatasets.setNdtset(Integer.parseInt(ndtset_S));
            allDatasets.setUsedtsets(true);
        }
        
        String udtset_S = mapString.get("udtset");
        
        if(udtset_S != null)
        {
            allDatasets.setUseudtset(true);
            String[] split = udtset_S.split(" ");
            if(split.length != 2)
            {
                throw new InvalidInputFileException("Udtset should contain exactly two int");
            }
            int[] udtset = new int[2];
            udtset[0] = Integer.parseInt(split[0]);
            udtset[1] = Integer.parseInt(split[1]);
            allDatasets.setUdtset(udtset);
            
            if(allDatasets.isUsedtsets())
            {
                if(allDatasets.getUdtset()[0]*allDatasets.getUdtset()[1] != allDatasets.getNdtset())
                {
                    throw new InvalidInputFileException("Ndtset should be the product of udtsets");
                }
                if(allDatasets.getUdtset()[0] > 999 || allDatasets.getUdtset()[0] < 1)
                {
                    throw new InvalidInputFileException("Udtset(1) should be between 1 and 999");
                }
                if(allDatasets.getUdtset()[1] > 9 || allDatasets.getUdtset()[1] < 1)
                {
                    throw new InvalidInputFileException("Udtset(2) should be between 1 and 9");
                }
            }
            allDatasets.setUsedtsets(true);
        }
        
        String jdtset_S = mapString.get("jdtset");
        
        if(jdtset_S == null)
        {
            if(allDatasets.isUseudtset())
            {
                for(int i = 1; i <= allDatasets.getUdtset()[0]; i++)
                {
                    for(int j = 1; j <= allDatasets.getUdtset()[1]; j++)
                    {
                        allDatasets.getJdtsets().add((i*10+j));
                    }
                }
                allDatasets.setUsejdtset(true);
            }
            else if(allDatasets.isUsedtsets())
            {
                for(int i = 1; i <= allDatasets.getNdtset(); i++)
                {
                    allDatasets.getJdtsets().add(i);
                }
                allDatasets.setUsejdtset(true);
            }
            else
            {
                allDatasets.setUsejdtset(false);
            }
            // Range from 1 to ndtset
        }
        else
        {
            if(allDatasets.isUseudtset())
            {
                throw new InvalidInputFileException("udtset and jdtset cannot be used at the same time !");
            }
            else
            {
                String[] split = jdtset_S.split(" ");
                for(String s : split)
                {
                    try
                    {
                        allDatasets.addJdtset(new Integer(s));
                        allDatasets.getJdtsets().add(new Integer(s));
                    }
                    catch(NumberFormatException e)
                    {
                        throw new InvalidInputFileException("Error in the syntax of jdtset");
                    }
                }
                allDatasets.setUsejdtset(true);
            }
        }
        
        //System.out.println("jdtsets = "+getJdtsets());
        allDatasets.clean();
        
        if(allDatasets.isUsejdtset())
        {
            for(int idtset : allDatasets.getJdtsets())
            {
                HashMap<String,ArrayList<Object>> valueArray = firstRead(mapString, idtset);
                AbinitDataset values = readDataSet2(valueArray, idtset);
                
                allDatasets.setDataset(idtset, values);
            }
        }
        else
        {
            HashMap<String,ArrayList<Object>> valueArray = firstRead(mapString,0);
            AbinitDataset values = readDataSet2(valueArray,0);
            
            allDatasets.setDataset(0, values);
        }
        
        //System.out.println(getAllDatasets());
        
        return allDatasets;
        
    }
    
    private HashMap<String, ArrayList<Object>> firstRead(HashMap<String, String> mapString, int idtset) 
        throws InvalidInputFileException
    {
        HashMap<String,ArrayList<Object>> curMap = new HashMap<>();
        
        Iterator<String> iter = allInputs.getListKeys().iterator();

        int units = idtset%10; // TODO: Local var hides field
        int dozens = idtset/10;
        
        while(iter.hasNext())
        {
            String name = iter.next();
            
            Variable var = allInputs.getVar(name);
            String value = mapString.get(name);
            // Look for : and + and * possibilities !
            String cscolon = mapString.get(name+":");
            String csplus = mapString.get(name+"+");
            String cstimes = mapString.get(name+"*");

            String cs1colon = mapString.get(name+"?:");
            String cs1plus = mapString.get(name+"?+");
            String cs1times = mapString.get(name+"?*");

            String cs2colon = mapString.get(name+":?");
            String cs2plus = mapString.get(name+"+?");
            String cs2times = mapString.get(name+"*?");


            if(cscolon != null)
            {
                if(csplus != null)
                {
                    value = "=0+|"+cscolon+"|"+csplus;
                }
                else if(csplus != null)
                {
                    value = "=0*|"+cscolon+"|"+cstimes;
                }
                else
                {
                    throw new InvalidInputFileException("Check the increment for variable : "+name);
                }
            }
            else if(cs1colon != null)
            {
                if(cs1plus != null)
                {
                    value = "=1+|"+cs1colon+"|"+cs1plus;
                }
                else if(cs1plus != null)
                {
                    value = "=1*|"+cs1colon+"|"+cs1times;
                }
                else
                {
                    throw new InvalidInputFileException("Check the increment for variable : "+name);
                }
            }
            else if(cs2colon != null)
            {
                if(cs2plus != null)
                {
                    value = "=2+|"+cs2colon+"|"+cs2plus;
                }
                else if(cs2plus != null)
                {
                    value = "=2*|"+cs2colon+"|"+cs2times;
                }
                else
                {
                    throw new InvalidInputFileException("Check the increment for variable : "+name);
                }
            }
            String curVal = mapString.get(name+"?"+units);
            if(curVal != null)
            {
                // Parse the string to get the value
                value = curVal;
            }

            curVal = mapString.get(name+dozens+"?");
            if(curVal != null)
            {
                // Parse the string to get the value
                value = curVal;
            }

            curVal = mapString.get(name+idtset);
            if(curVal != null)
            {
                // Parse the string to get the value
                value = curVal;
            }
                    
            if(value != null)
            {
                ArrayList<Object> valueArray = getValue(name,value,var.getVartype(), var.getDimensions(),idtset);
                curMap.put(name,valueArray);
            }
        }
        
        return curMap;
    }
    
    private AbinitDataset readDataSet2(HashMap<String, ArrayList<Object>> mapData, int idtset) throws InvalidInputFileException {
        
        AbinitDataset dataset = new AbinitDataset();
        
        for(Entry<String,ArrayList<Object>> entry : mapData.entrySet())
        {
            String name = entry.getKey();
            ArrayList<Object> valueArray = entry.getValue();
            Variable var = allInputs.getVar(name);
            //Object o = getObjectFromArray(valueArray,name,var.getVartype(),var.getDimensions(), idtset, mapData);
            AbinitVariable curVar = new AbinitVariable();
            //curVar.setValue(o);
            curVar.setInputValue(valueArray);
            curVar.setDocVariable(var);
            dataset.setVariable(name,curVar);
        }
        
        return dataset;
    }
    
    public ArrayList<Object> getValue(String name, String text, String type, Object dimensions, int jdtset) throws InvalidInputFileException
    {
        
        //System.out.println("Reading variable "+name+" with text = "+text+", type = "+type+", dimensions = "+dimensions);
        
        ArrayList<Object> listValues = new ArrayList<>();
        
        if(text.startsWith("="))
        {
            String splitted[] = text.split("\\|");
            
            String cmd = splitted[0];
            String startVal = splitted[1];
            String incVal = splitted[2];
            
            int unities = (jdtset%10);
            int dozens = (jdtset/10);
            int number = 0;
            
            switch(cmd.charAt(1))
            {
                case '0':
                    number = (jdtset-1);
                    break;
                case '1':
                    number = (unities-1);
                    break;
                case '2':
                    number = (dozens-1);
                    break;
            }
            
            ArrayList<Object> o1 = getValue(name,startVal,type,dimensions,jdtset);
            ArrayList<Object> o2 = getValue(name,incVal,type,dimensions,jdtset);
            
            if(o1.size() != o2.size())
            {
                throw new InvalidInputFileException("The increment and the starting value should have the same length");
            }
                
            for(int i = 0; i < o1.size(); i++)
            {
                if(type.contains("integer"))
                {
                    int i1 = (int)o1.get(i);
                    int i2 = (int)o2.get(i);

                    if(cmd.charAt(2) == '+')
                    {
                        listValues.add(i1+i2*number);
                    }
                    else
                    {
                        listValues.add((int)(i1*(Math.pow(i2,number))));
                    }
                }
                else if(type.contains("real"))
                {
                    double i1 = (double)o1.get(i);
                    double i2 = (double)o2.get(i);

                    if(cmd.charAt(2) == '+')
                    {
                        listValues.add(i1+i2*number);
                    }
                    else
                    {
                        listValues.add((i1*(Math.pow(i2,number))));
                    }
                }
            }
        }
        else
        {
            String[] allText = text.split(" ");

            String unit = allText[allText.length-1];
            double scalingFactor = 1.0d;
            int nbValues = allText.length;
            boolean isUnit = false;
            if(isUnit(unit))
            {
                if(type.contains("integer"))
                {
                    throw new InvalidInputFileException("Units can only be provided for real numbers !");
                }
                scalingFactor = listOfUnits.get(unit.toUpperCase());
                nbValues--;
                isUnit = true;
            }

            for(int i = 0; i < nbValues; i++)
            {
                String curValue = allText[i];

                if(curValue.contains("*"))
                {
                    String[] multTab = curValue.split("\\*");
                    MultipleValue multVal = new MultipleValue();
                    Object value = null;
                    Object number = null;
                    if(multTab.length == 1)
                    {
                        value = readData(multTab[0],type);
                    }
                    else if(multTab.length == 2)
                    {
                        number = readData(multTab[0],type);
                        value = readData(multTab[1],type);
                    }
                    if(isUnit)
                    {
                        if(value instanceof Double)
                        {
                            value = scalingFactor*(Double)value;
                        }
                        else
                        {
                            System.err.println("values with units should be numbers !");
                        }
                    }
                    multVal.setNumber(number);
                    multVal.setValue(value);
                    listValues.add(multVal);
                }
                else
                {
                    if(isUnit)
                    {
                        listValues.add(scalingFactor*(Double)readData(curValue,type));
                    }
                    else
                    {
                        listValues.add(readData(curValue,type));
                    }
                }
            }
        }
        return listValues;
    }
    
    public Number readData(String text, String type) throws InvalidInputFileException
    {
        Double val = null;
        
        if(text == null || text.isEmpty())
        {
            return null;
        }
        
        if(type.contains("integer"))
        {
            int i = -1;
            try{
                
                i = Integer.parseInt(text);
            }
            catch(NumberFormatException ex)
            {
                throw new InvalidInputFileException("Error parsing text : "+text+" [should be integer]");
            }
            return i;
        }
        else if(type.contains("real"))
        {
            text = text.replace("d", "e");

            if(text.contains("/"))
            {
                String[] splitted = text.split("/");
                Double d1 = (Double)readData(splitted[0],type);
                Double d2 = (Double)readData(splitted[1],type);
                val = d1/d2;
            }
            else if(text.startsWith("sqrt(") || text.startsWith("SQRT("))
            {
                String sub= text.substring(5,text.length()-1);
                Double v = (Double)readData(sub,type);
                return Math.sqrt(v);
            }
            else if(text.startsWith("-sqrt(") || text.startsWith("-SQRT("))
            {
                String sub= text.substring(6,text.length()-1);
                Double v = (Double)readData(sub,type);
                return -Math.sqrt(v);
            }
            else
            {
               // System.out.println("ParseDouble : "+text);
                try
                {
                    val = Double.parseDouble(text);
                }
                catch(NumberFormatException ex)
                {
                    throw new InvalidInputFileException("Error parsing text : "+text+" [should be real]");
                }
            }

            return val;
        }
        else
        {
            return null;
        }
    }

    private static class InvalidInputFileException extends IOException {

        public InvalidInputFileException(String unit_without_values_) {
            super(unit_without_values_);
        }
    }

}
