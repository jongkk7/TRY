package mars.nomad.com.b0_generaltemplate;

import java.util.ArrayList;
import java.util.List;

import mars.nomad.com.a0_common.DataBase.Room.NsTemplate.NsFile;
import mars.nomad.com.a0_common.DataBase.Room.NsTemplate.NsTemplate;
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

            setEmptyCustomView(templateList);

            setLegacyAdapterActivity(templateList);

            setBasicViewPager(templateList);

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

                add(new NsFile(R.raw.java_empty_activity, "", "Activity{$Data}.java"));
                add(new NsFile(R.raw.java_empty_viewmodel, "/mvvm", "{$Data}ViewModel.java"));
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

                add(new NsFile(R.raw.java_list_adapter_activity, "", "Activity{$Data}.java"));
                add(new NsFile(R.raw.xml_list_adapter, "/layout", "{$res_name}.xml"));
                add(new NsFile(R.raw.xml_list_adapter_activity, "/layout", "{$activity_xml_name}.xml"));

                add(new NsFile(R.raw.java_list_adapter_adapter, "/Adapter", "Adapter{$Data}.java"));
                add(new NsFile(R.raw.java_list_adapter_click_listener, "/Adapter/ClickListener", "{$Data}ClickListener.java"));
                add(new NsFile(R.raw.java_list_adapter_data_model, "/Adapter/DataModel", "{$Data}DataModel.java"));

                add(new NsFile(R.raw.java_list_adapter_view_model, "/mvvm", "{$Data}ViewModel.java"));
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

                add(new NsFile(R.raw.java_void_fragment, "", "Fragment{$Data}.java"));
                add(new NsFile(R.raw.xml_void_fragment, "", "{$res_name}.xml"));
                add(new NsFile(R.raw.java_empty_viewmodel, "/mvvm", "{$Data}ViewModel.java"));
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
            listAdapterTemplate.setDescription("룸용 탬플릿. txt파일도 하나 생성됨. 거기보면 NsDataBase랑 BaseApplication에 넣을 코드까지 들어있음.");
            listAdapterTemplate.setTemplateFiles(new ArrayList<NsFile>() {{

                add(new NsFile(R.raw.java_room_object, "", "{$Data}.java"));
                add(new NsFile(R.raw.java_room_dao, "", "{$Data}Dao.java"));
                add(new NsFile(R.raw.java_room_repository, "", "{$Data}Repository.java"));
                add(new NsFile(R.raw.txt_room_dao_text, "", "roomInfo.txt"));
            }});

            templateList.add(listAdapterTemplate);

        } catch (Exception e) {
            ErrorController.showError(e);
        }
    }

    /**
     * 빈 커스텀뷰
     *
     * @param templateList
     */
    private static void setEmptyCustomView(List<NsTemplate> templateList) {

        try {

            NsTemplate template = new NsTemplate();

            template.setTemplateName("CustomView");
            template.setDescription("빈 커스텀뷰. attrs까지 선언되어 있음.(비어 있다.)");
            template.setTemplateFiles(new ArrayList<NsFile>() {{

                add(new NsFile(R.raw.java_custom_view, "", "Layout{$Data}.java"));
                add(new NsFile(R.raw.xml_custom_view, "/layout", "{$res_name}.xml"));
                add(new NsFile(R.raw.xml_attrs_custom_view, "", "attrs.xml"));
            }});

            templateList.add(template);

        } catch (Exception e) {
            ErrorController.showError(e);
        }
    }

    /**
     * 레거시 어댑터 + 액티비티 + mvvm 생성
     *
     * @param templateList
     */
    private static void setLegacyAdapterActivity(List<NsTemplate> templateList) {

        try {

            NsTemplate template = new NsTemplate();

            template.setTemplateName("LegacyAdapterActivity");
            template.setDescription("RecyclerView.Adapter 및 mvvm과 액티비티 포함 모든 것.");
            template.setTemplateFiles(new ArrayList<NsFile>() {{

                add(new NsFile(R.raw.java_adapter, "/Adapter", "Adapter{$Data}.java"));
                add(new NsFile(R.raw.java_adapter_activity, "", "Activity{$Data}.java"));
                add(new NsFile(R.raw.java_adapter_datamodel, "/DataModel", "{$Data}DataModel.java"));
                add(new NsFile(R.raw.java_adapter_viewmodel, "/mvvm", "{$Data}ViewModel.java"));
                add(new NsFile(R.raw.xml_adapter_activity, "/layout", "{$activity_res_id}.xml"));
                add(new NsFile(R.raw.xml_adapter_cell, "/layout", "{$adapter_cell_id}.xml"));
                add(new NsFile(R.raw.txt_adapter_activity, "", "ActivityRegister.txt"));
            }});

            templateList.add(template);

        } catch (Exception e) {
            ErrorController.showError(e);
        }
    }

    /**
     * 뷰페이저 기본형
     *
     * @param templateList
     */
    private static void setBasicViewPager(List<NsTemplate> templateList) {

        try {

            NsTemplate template = new NsTemplate();

            template.setTemplateName("BasicViewPager");
            template.setDescription("액티비티, 뷰페이저, mvvm, 베이스 프래그먼트");
            template.setTemplateFiles(new ArrayList<NsFile>() {{

                add(new NsFile(R.raw.java_activity_viewpager, "", "Activity{$Data}.java"));
                add(new NsFile(R.raw.xml_activity_viewpager, "/layout", "activity_viewpager_{$Data_lower}.xml"));
                add(new NsFile(R.raw.java_adapter_viewpager, "/Adapter", "Adapter{$Data}Pager.java"));
                add(new NsFile(R.raw.java_viewpager_view_model, "/Mvvm", "{$Data}ViewModel.java"));
                add(new NsFile(R.raw.java_viewpager_fragment, "/Fragment", "Base{$Data}Fragment.java"));
                add(new NsFile(R.raw.xml_void_fragment, "/layout", "fragment_viewpager_{$Data_low}.xml"));

            }});

            templateList.add(template);

        } catch (Exception e) {
            ErrorController.showError(e);
        }
    }
}
