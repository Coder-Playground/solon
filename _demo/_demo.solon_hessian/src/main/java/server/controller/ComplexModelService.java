package server.controller;

import org.noear.solon.annotation.XBean;
import org.noear.solon.annotation.XMapping;
import org.noear.solon.core.XMethod;
import server.dso.IComplexModelService;
import server.model.ComplexModel;

import java.util.HashMap;
import java.util.Map;

@XMapping(value = "/ComplexModelService/",method = {XMethod.ALL})
@XBean(remoting = true)
public class ComplexModelService implements IComplexModelService {
    private Map<Integer,ComplexModel> models = new HashMap<Integer, ComplexModel>();


    @Override
    public void save(ComplexModel model) {
        if (model.getId() == null){
            throw new IllegalArgumentException("id could not be null");
        }

        models.put(model.getId(), model);
    }

    @Override
    public ComplexModel read(Integer modelId) {
        return models.get(modelId);
    }
}
