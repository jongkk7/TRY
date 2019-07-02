package mars.nomad.com.b0_generaltemplate;

import java.util.ArrayList;
import java.util.List;

import mars.nomad.com.b0_generaltemplate.DataModel.NsFile;
import mars.nomad.com.b0_generaltemplate.DataModel.NsTemplate;
import mars.nomad.com.l0_base.Logger.ErrorController;

/**
 * Created by 김창혁, NomadSoft.Inc on 2019-07-02.
 */
public class GeneralTemplateEngine {

    public static List<NsTemplate> InitializeTemplateEngine() {

        List<NsTemplate> templateList = new ArrayList<>();

        try {

            setVoidActivity(templateList);

            setListAdapterActivity(templateList);

        } catch (Exception e) {
            ErrorController.showError(e);
        }
        return templateList;
    }


    /**
     * 빈 액티비티 (mvvm)
     * @param templateList
     */
    private static void setVoidActivity(List<NsTemplate> templateList) {

        try{

            NsTemplate voidActivityTemplate = new NsTemplate();

            voidActivityTemplate.setTemplateName("VoidActivity");
            voidActivityTemplate.setDescription("빈 액티비티, xml, mvvm");
            voidActivityTemplate.setTemplateFiles(new ArrayList<NsFile>() {{
                add(new NsFile(R.raw.java_empty_activity, "", "Activity{$name}.java"));
                add(new NsFile(R.raw.java_empty_viewmodel, "/mvvm", "{$name}ViewModel.java"));
                add(new NsFile(R.raw.xml_empty_layout, "/layout", "{$res_name}.xml"));
            }});

            templateList.add(voidActivityTemplate);
        }catch (Exception e){
            ErrorController.showError(e);
        }
    }

    /**
     * 리스트 어댑터 액티비티
     * @param templateList
     */
    private static void setListAdapterActivity(List<NsTemplate> templateList) {

        try{

            NsTemplate listAdapterTemplate = new NsTemplate();

            listAdapterTemplate.setTemplateName("ListAdapterActivity");
            listAdapterTemplate.setDescription("리스트 어댑터 생성을 위한 탬플릿");
            listAdapterTemplate.setTemplateFiles(new ArrayList<NsFile>() {{
                add(new NsFile(R.raw.java_list_adapter_activity, "", "Activity{$Data}.java"));
                add(new NsFile(R.raw.xml_list_adapter, "/layout", "{$res_name}.xml"));
                add(new NsFile(R.raw.xml_list_adapter_activity, "/layout", "{$activity_xml_name}.xml"));

                add(new NsFile(R.raw.java_list_adapter_adapter, "/Adapter", "Adapter{$Data}.java"));
                add(new NsFile(R.raw.java_list_adapter_click_listener, "/Adapter/ClickListener", "{$Data}ClickListener.java"));
                add(new NsFile(R.raw.java_list_adapter_data_model, "/Adapter/DataModel", "{$Data}DataModel.java"));

                add(new NsFile(R.raw.java_list_adapter_view_model, "/mvvm", "{$Data}ViewModel.java"));
            }});

            templateList.add(listAdapterTemplate);

        }catch (Exception e){
            ErrorController.showError(e);
        }
    }
}
