package com.uni.ailab.scp.runtime;

/**
 * Created by gabriele on 27/03/15.
 */

import android.util.Log;

import org.sat4j.minisat.SolverFactory;
import org.sat4j.specs.ContradictionException;
import org.sat4j.specs.IProblem;
import org.sat4j.specs.ISolver;
import org.sat4j.specs.IVec;
import org.sat4j.specs.IVecInt;
import org.sat4j.specs.TimeoutException;

import com.uni.ailab.scp.log.Logger;

public class ScpRuntime {

    private static Configuration configuration = new Configuration();
    
    public static boolean canPop(String component) {

        ISolver solver = SolverFactory.newLight();
        IVec<IVecInt> clauses = configuration.encodePop(component);

        try {
            solver.addAllClauses(clauses);
            IProblem problem = solver;
            
            Logger.log("CHECKING SAT pop");
            long cTime = System.currentTimeMillis();
            
            if (problem.isSatisfiable()) {
            	Logger.log("SAT in " + (System.currentTimeMillis() - cTime));
                return true;
            }
            else {
            	Logger.log("UNSAT in " + (System.currentTimeMillis() - cTime));
            	return false;
            }
        } catch (ContradictionException e) {
            Log.w(ScpRuntime.class.toString(), e.toString());
            return false;
        } catch (TimeoutException e) {
            Log.w(ScpRuntime.class.toString(), e.toString());
            return false;
        }
    }

    public static boolean canAlloc(Frame f, String component) {

        ISolver solver = SolverFactory.newLight();
        IVec<IVecInt> clauses = configuration.encodePush(f, component, true);

        try {
            solver.addAllClauses(clauses);
            IProblem problem = solver;
            
            Logger.log("CHECKING SAT alloc");
            long cTime = System.currentTimeMillis();
            
            if (problem.isSatisfiable()) {
            	Logger.log("SAT in " + (System.currentTimeMillis() - cTime));
                return true;
            }
            else {
            	Logger.log("UNSAT in " + (System.currentTimeMillis() - cTime));
            	return false;
            }
        } catch (ContradictionException e) {
            Log.w(ScpRuntime.class.toString(), e.toString());
            return false;
        } catch (TimeoutException e) {
            Log.w(ScpRuntime.class.toString(), e.toString());
            return false;
        }
    }

    public static boolean canPush(Frame f, String component) {

        ISolver solver = SolverFactory.newLight();
        IVec<IVecInt> clauses = configuration.encodePush(f, component, false);

        try {
            solver.addAllClauses(clauses);
            IProblem problem = solver;
            
            Logger.log("CHECKING SAT push");
            long cTime = System.currentTimeMillis();
            
            if (problem.isSatisfiable()) {
            	Logger.log("SAT in " + (System.currentTimeMillis() - cTime));
                return true;
            }
            else {
            	Logger.log("UNSAT in " + (System.currentTimeMillis() - cTime));
            	return false;
            }
        } catch (ContradictionException e) {
            Log.w(ScpRuntime.class.toString(), e.toString());
            return false;
        } catch (TimeoutException e) {
            Log.w(ScpRuntime.class.toString(), e.toString());
            return false;
        }
    }

    public static void pop(String component) {

        configuration.pop(component);

    }

    public static void alloc(Frame f, String component) {
        
        configuration.push(f, component, true);
        
    }

    public static void push(Frame f, String component) {

        configuration.push(f, component, false);
        
    }
    
    public static String[] getStackRoots() {
    	String[] s = configuration.getStackRoots();
    	Logger.log("Current Roots: " + s.toString());
    	return s;
    	
    }
    
    public static String[] getStackElements(String root) {
    	return configuration.getStackElements(root);
    }
    
    public static Frame getFrame(String root, String comp) {
    	return configuration.getFrame(root, comp);
    }

}
