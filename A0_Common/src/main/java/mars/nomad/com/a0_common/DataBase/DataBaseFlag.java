package mars.nomad.com.a0_common.DataBase;

import mars.nomad.com.l0_base.Logger.ErrorController;

/**
 * Created by 김창혁, NomadSoft.Inc on 2019-05-07.
 */
public class DataBaseFlag {


    private static final boolean useLitePal = false;

    private static final boolean useRoom = true;


    public static boolean isUseLitePal() {
        return useLitePal;
    }

    public static boolean isUseRoom(){
        return useRoom;
    }
}
