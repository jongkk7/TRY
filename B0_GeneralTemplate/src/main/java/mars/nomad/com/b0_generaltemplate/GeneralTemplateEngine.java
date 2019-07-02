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

            setVoidFragment(templateList);

            setListAdapterActivity(templateList);

            setRoom(templateList);

        } catch (Exception e) {
            ErrorController.showError(e);
        }
        return templateList;
    }


    /**
     * 빈 액티비티 (mvvm)
     *
     * @param templateList
     */
    private static void setVoidActivity(List<NsTemplate> templateList) {

        try {

            NsTemplate voidActivityTemplate = new NsTemplate();

            voidActivityTemplate.setTemplateName("VoidActivity");
            voidActivityTemplate.setDescription("빈 액티비티, xml, mvvm");
            voidActivityTemplate.setTemplateFiles(new ArrayList<NsFile>() {{
                add(new NsFile(R.raw.java_empty_activity, "/{$Data}", "Activity{$Data}.java"));
                add(new NsFile(R.raw.java_empty_viewmodel, "/{$Data}/mvvm", "{$Data}ViewModel.java"));
                add(new NsFile(R.raw.xml_empty_layout, "/layout", "{$res_name}.xml"));
            }});

            templateList.add(voidActivityTemplate);
        } catch (Exception e) {
            ErrorController.showError(e);
        }
    }

    /**
     * 리스트 어댑터 액티비티
     *
     * @param templateList
     */
    private static void setListAdapterActivity(List<NsTemplate> templateList) {

        try {

            NsTemplate listAdapterTemplate = new NsTemplate();

            listAdapterTemplate.setTemplateName("ListAdapterActivity");
            listAdapterTemplate.setDescription("리스트 어댑터 생성을 위한 탬플릿");
            listAdapterTemplate.setTemplateFiles(new ArrayList<NsFile>() {{
                add(new NsFile(R.raw.java_list_adapter_activity, "/{$Data}", "Activity{$Data}.java"));
                add(new NsFile(R.raw.xml_list_adapter, "/layout", "{$res_name}.xml"));
                add(new NsFile(R.raw.xml_list_adapter_activity, "/layout", "{$activity_xml_name}.xml"));

                add(new NsFile(R.raw.java_list_adapter_adapter, "/{$Data}/Adapter", "Adapter{$Data}.java"));
                add(new NsFile(R.raw.java_list_adapter_click_listener, "/{$Data}/Adapter/ClickListener", "{$Data}ClickListener.java"));
                add(new NsFile(R.raw.java_list_adapter_data_model, "/{$Data}/Adapter/DataModel", "{$Data}DataModel.java"));

                add(new NsFile(R.raw.java_list_adapter_view_model, "/{$Data}/mvvm", "{$Data}ViewModel.java"));
            }});

            templateList.add(listAdapterTemplate);

        } catch (Exception e) {
            ErrorController.showError(e);
        }
    }


    /**
     * 빈 프래그먼트
     *
     * @param templateList
     */
    private static void setVoidFragment(List<NsTemplate> templateList) {

        try {

            NsTemplate template = new NsTemplate();

            template.setTemplateName("VoidFragment");
            template.setDescription("빈 프래그먼트, xml, mvvm");
            template.setTemplateFiles(new ArrayList<NsFile>() {{

                add(new NsFile(R.raw.java_void_fragment, "/{$Data}", "Fragment{$Data}.java"));
                add(new NsFile(R.raw.xml_void_fragment, "/{$Data}", "{$res_name}.xml"));
                add(new NsFile(R.raw.java_empty_viewmodel, "/{$Data}/mvvm", "{$Data}ViewModel.java"));
            }});

            templateList.add(template);

        } catch (Exception e) {
            ErrorController.showError(e);
        }
    }

    /**
     * 룸
     *
     * @param templateList
     */
    private static void setRoom(List<NsTemplate> templateList) {

        try {

            NsTemplate listAdapterTemplate = new NsTemplate();

            listAdapterTemplate.setTemplateName("Room");
            listAdapterTemplate.setDescription("룸용 탬플릿. txt파일도 하나 생성됨. 거기보면 NsDataBase랑 BaseApplication에 넣을 코드까지 다 들어있음.");
            listAdapterTemplate.setTemplateFiles(new ArrayList<NsFile>() {{

                add(new NsFile(R.raw.java_room_object, "/{$Data}", "{$Data}.java"));
                add(new NsFile(R.raw.java_room_dao, "/{$Data}", "{$Data}Dao.java"));
                add(new NsFile(R.raw.java_room_repository, "/{$Data}", "{$Data}Repository.java"));
                add(new NsFile(R.raw.txt_room_dao_text, "", "roomInfo.txt"));
            }});

            templateList.add(listAdapterTemplate);

        } catch (Exception e) {
            ErrorController.showError(e);
        }
    }

}
