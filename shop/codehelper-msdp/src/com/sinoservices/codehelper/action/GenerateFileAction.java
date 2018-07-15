package com.sinoservices.codehelper.action;

import com.sinoservices.codehelper.Environment;
import com.sinoservices.codehelper.model.ResultModel;
import com.sinoservices.codehelper.model.layout.PageLayout;
import com.sinoservices.codehelper.model.layout.Resource;
import com.sinoservices.codehelper.model.layout.TemplateLayout;
import com.sinoservices.codehelper.util.CharacterUtils;
import com.sinoservices.codehelper.util.ObjectConversionUtils;
import com.sinoservices.codehelper.web.ActionContext;
import com.sinoservices.codehelper.web.HttpAction;
import com.sinoservices.codehelper.web.JSONMessage;
import freemarker.template.Configuration;
import freemarker.template.DefaultObjectWrapper;
import freemarker.template.Template;
import freemarker.template.TemplateException;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.text.SimpleDateFormat;
import java.util.*;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

public class GenerateFileAction extends HttpAction {
    private Environment environment = null;

    public GenerateFileAction(Environment environment) {
        this.environment = environment;
    }

    public JSONMessage handle(ActionContext context) throws IOException {
        PageLayout pageLayout = ObjectConversionUtils.jsonToPage(context.getJSONObject(),
                Integer.parseInt(this.environment.getColSize()));

        String path = context.getRequest().getSession().getServletContext()
                .getRealPath("/WEB-INF/classes/");

        Map params = new HashMap();
        params.put("page", pageLayout);
        params.put("tableName", CharacterUtils
                .replaceUnderlineAndfirstToUpper(pageLayout.getResource()
                        .getSelectedTable()));
        params.put("packageName",
                this.environment.getPackageName().replaceAll("/", "."));
        params.put("moduleName", this.environment.getMoudleName().toString()
                .replaceAll("/", "."));
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd HH:mm");
        String nowDate = sdf.format(new Date());
        params.put("nowDate",nowDate);
        sdf = new SimpleDateFormat("yyyy年MM月dd日");
        String nowDateZh = sdf.format(new Date());
        params.put("nowDateZh",nowDateZh);
        params.put("author",environment.getAuthor());

        Configuration cfg = new Configuration();
        cfg.setDirectoryForTemplateLoading(new File(path));
        cfg.setObjectWrapper(new DefaultObjectWrapper());

        TemplateLayout[] templateLayoutArray = pageLayout
                .getTemplateLayoutArray();
        JSONMessage jsonMessage = new JSONMessage();

        File f1 = new File("C://java//src//");
        if (!f1.exists()) {
        	f1.mkdirs();
        }
        List resultList = new ArrayList();
        for (TemplateLayout templateLayout : templateLayoutArray) {
            ResultModel resultModel = new ResultModel();
            String templateDir = templateLayout.getTemplateDir();
            String saveDir = templateLayout.getSaveDir();
            saveDir = "C://java//src//" + saveDir.substring(saveDir.lastIndexOf("/"), saveDir.length());
            Template temp = cfg.getTemplate(templateDir, "utf-8");
            File f = new File(saveDir);
            if (!f.exists()) {
                f.createNewFile();
            }
            Writer out = new OutputStreamWriter(new FileOutputStream(f),
                    "utf-8");
            try {
                temp.process(params, out);
                resultModel.setFileName(saveDir.substring(
                        saveDir.lastIndexOf("/") + 1, saveDir.length()));
                resultModel.setFilePath(saveDir);
                resultModel.setResult(Boolean.valueOf(true));
            } catch (TemplateException e) {
                e.printStackTrace();
                resultModel.setResult(Boolean.valueOf(false));
            }
            resultList.add(resultModel);
        }
        jsonMessage.setData(resultList.toArray());
        return jsonMessage;
    }
}
