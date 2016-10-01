package com.smart.webapp.util;

import com.smart.model.lis.CalculateFormula;
import com.smart.service.lis.CalculateFormulaManager;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by yuzh on 2016/9/20.
 */
public class CalculateFormulaMapUtil {

    private Map<String, List<CalculateFormula>> formulaMap = null;
    private static CalculateFormulaMapUtil instance = new CalculateFormulaMapUtil();
    private CalculateFormulaMapUtil() {}

    public static CalculateFormulaMapUtil getInstance(CalculateFormulaManager calculateFormulaManager) {
        if(instance.formulaMap == null){
            instance.formulaMap = new HashMap<String, List<CalculateFormula>>();
            List<CalculateFormula> list = calculateFormulaManager.getAll();
            for (CalculateFormula cf : list) {
                if(instance.formulaMap.containsKey(cf.getLab())) {
                    instance.formulaMap.get(cf.getLab()).add(cf);
                } else {
                    List<CalculateFormula> calculateFormulas = new ArrayList<CalculateFormula>();
                    calculateFormulas.add(cf);
                    instance.formulaMap.put(cf.getLab(), calculateFormulas);
                }
            }
        }
        return instance;
    }

    public Map<String, List<CalculateFormula>> getFormulaMap(){
        return formulaMap;
    }

    public void updateFormulaMap(CalculateFormula calculateFormula) {
        if(formulaMap.containsKey(calculateFormula.getLab())) {
            formulaMap.get(calculateFormula.getLab()).add(calculateFormula);
        } else {
            List<CalculateFormula> calculateFormulas = new ArrayList<CalculateFormula>();
            calculateFormulas.add(calculateFormula);
            formulaMap.put(calculateFormula.getLab(), calculateFormulas);
        }
    }

    public void removeFromMap(CalculateFormula calculateFormula) {
        formulaMap.get(calculateFormula.getLab()).remove(calculateFormula);
    }
}
