package utils;

import basicobjects.Checker;
import basicobjects.Cont;

import java.util.LinkedList;

/**
 * Created by Lemming on 30.07.2014.
 */
public class DependencyChecker {
    public static void physicalIsParentOfPhysical(Cont world){
        LinkedList<Cont> physical = world.getAllThisAndChildren(new Checker.IsPlatformChecker());
        LinkedList<Exception> exceptions = new LinkedList<Exception>();

        for(Cont cont1 : physical)
            for(Cont cont2 : physical)
                if(cont1.hasParent(cont2)) exceptions.add(new Exception("physicalIsParentOfPhysical"));

        for(Exception e : exceptions){
            try {
                throw e;
            } catch (Exception e1) {
                e1.printStackTrace();
            }
        }

    }

}
