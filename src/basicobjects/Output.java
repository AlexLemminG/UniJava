package basicobjects;

import java.util.LinkedList;
import java.util.Map;
import java.util.TreeMap;

/**
 * Created by Lemming on 01.08.2014.
 */
public class Output {
    private Map<String, Object> map = new TreeMap<String, Object>();
    private LinkedList<String> toOutput = new LinkedList<String>();

    public void put(String key, Object value){
        map.containsKey(key);
        map.put(key, value);
    }

    public void addOutput(String key){
        if(!map.containsKey(key))
            try {
                throw new Exception("NO KEY :" + key);
            } catch (Exception e) {
                e.printStackTrace();
            }
        if(!toOutput.contains(key))
            toOutput.add(key);
    }

    public boolean removeOutput(String key){
        return toOutput.remove(key);
    }

    public String get(String key){
        Object value = map.get(key);
        String result;
        if(value != null)
            result = map.get(key).toString();
        else
            result = "NULL";
        return key + ": " + result;
    }

    public int size(){
        return map.size();
    }

    public int outputSize(){
        return toOutput.size();
    }

    public LinkedList<String> getToOutput(){
        LinkedList<String> result = new LinkedList<String>();
        for(String key : toOutput){
            result.add(get(key));
        }
        return result;
    }


}
