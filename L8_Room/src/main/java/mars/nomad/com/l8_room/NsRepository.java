package mars.nomad.com.l8_room;

import android.app.Application;
import android.os.AsyncTask;

import java.util.List;

/**
 * Created by 김창혁, NomadSoft.Inc on 2019-01-04.
 */
public abstract class NsRepository<T> {

    protected NsDao<T> nsDao;

    public abstract void initiateDao(Application application);


    public void PRINT() {

        try {

            new FindAllAsyncTask<T>(nsDao, new RDBCallback.QueryCallback<List<T>>() {
                @Override
                public void onSuccess(List<T> result) {

                    for (T t : result) {

                        RdbErrorController.showMessage(t.toString());
                    }
                }
            }).execute();

        } catch (Exception e) {
            RdbErrorController.showError(e);
        }
    }

    public void save(T item) {
        new SaveItemAsyncTask(nsDao).execute(item);
    }

    public void insert(T item) {
        new InsertItemAsyncTask(nsDao).execute(item);
    }

    /**
     * 2019-01-21 SJH 인서트 후 콜백을 돌려주는 insert식
     *
     * @param item
     * @param callback
     */
    public void insertWithCallback(T item, RDBCallback.SingleObjectCallback callback) {

        new InsertItemWithCallbackAsyncTask(nsDao, callback).execute(item);
    }

    public void insertAll(List<T> items) {

        new InsertAllItemAsyncTask(nsDao, items).execute();
    }

    public void saveAll(List<T> items) {
        new SaveAllItemAsyncTask<T>(nsDao, items).execute();
    }

    /**
     * 2019-01-14 SJH 어싱크가 아닌 그냥 쿼리만을 실행한다.
     * 반드시 AsyncTask안에서 호출되어야 한다.
     *
     * 2019-01-25 김창혁 배열을 List로 변경함
     */
    public void insertAllSync(List<T> items) {

        nsDao.insertAll(items);
    }

    public void update(T item) {

        new UpdateItemAsyncTask(nsDao).execute(item);
    }

    /**
     * 2019-01-21 SJH 업데이트 후 콜백을 돌려주는 update식
     *
     * @param item
     */
    public void updateWithCallback(T item, RDBCallback.SingleObjectCallback callback) {

        new UpdateItemWithCallbackAsyncTask(nsDao, callback).execute(item);
    }

    public void delete(T item) {
        new DeleteItemAsyncTask(nsDao).execute(item);
    }

    public void deleteAllItems() {
        new DeleteAllItemAsyncTask(nsDao).execute();
    }

    /**
     * 2019-01-14 SJH 어싱크가 아닌 그냥 쿼리만을 실행한다.
     * 반드시 AsyncTask안에서 호출되어야 한다.
     */
    public void deleteAllItemsSync() {

        nsDao.deleteAll();
    }

    public void findAll(final RDBCallback.QueryCallback<List<T>> callback) {

        try {

            new FindAllAsyncTask<T>(nsDao, callback).execute();

        } catch (Exception e) {
            RdbErrorController.showError(e);
        }
    }


    private static class FindAllAsyncTask<T> extends AsyncTask<Void, Void, List<T>> {

        private NsDao<T> nsDao;
        private RDBCallback.QueryCallback<List<T>> callback;

        public FindAllAsyncTask(NsDao nsDao, RDBCallback.QueryCallback<List<T>> callback) {
            this.nsDao = nsDao;
            this.callback = callback;
        }

        @Override
        protected List<T> doInBackground(Void... ts) {

            List<T> result = nsDao.findAll();
            return result;
        }

        @Override
        protected void onPostExecute(List<T> ts) {

            callback.onSuccess(ts);
        }
    }


    private static class SaveItemAsyncTask<T> extends AsyncTask<T, Void, Void> {

        private NsDao<T> nsDao;

        public SaveItemAsyncTask(NsDao nsDao) {
            this.nsDao = nsDao;
        }

        @Override
        protected Void doInBackground(T... item) {

            nsDao.save(item[0]);
            return null;
        }
    }

    private static class SaveAllItemAsyncTask<T> extends AsyncTask<Void, Void, Void> {

        private final List<T> addItem;
        private NsDao<T> nsDao;

        public SaveAllItemAsyncTask(NsDao nsDao, List<T> addItem) {
            this.nsDao = nsDao;
            this.addItem = addItem;
        }

        @Override
        protected Void doInBackground(Void... item) {

            nsDao.saveAll(addItem);
            return null;
        }
    }

    private static class InsertItemAsyncTask<T> extends AsyncTask<T, Void, Void> {

        private NsDao<T> nsDao;

        public InsertItemAsyncTask(NsDao nsDao) {
            this.nsDao = nsDao;
        }

        @Override
        protected Void doInBackground(T... item) {

            nsDao.insert(item[0]);
            return null;
        }
    }

    private static class InsertItemWithCallbackAsyncTask<T> extends AsyncTask<T, Void, Void> {

        private NsDao<T> nsDao;
        private RDBCallback.SingleObjectCallback callback;

        public InsertItemWithCallbackAsyncTask(NsDao nsDao, RDBCallback.SingleObjectCallback callback) {
            this.nsDao = nsDao;
            this.callback = callback;
        }

        @Override
        protected Void doInBackground(T... item) {

            nsDao.insert(item[0]);
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {

            if (callback != null) {

                callback.onSuccess("");
            }
        }
    }

    private static class InsertAllItemAsyncTask<T> extends AsyncTask<Void, Void, Void> {

        private final List<T> addItmes;
        private NsDao<T> nsDao;

        public InsertAllItemAsyncTask(NsDao nsDao, List<T> addItmes) {
            this.nsDao = nsDao;
            this.addItmes = addItmes;
        }

        @Override
        protected Void doInBackground(Void... item) {

            nsDao.insertAll(addItmes);
            return null;
        }
    }

    private static class UpdateItemAsyncTask<T> extends AsyncTask<T, Void, Void> {

        private NsDao<T> nsDao;

        public UpdateItemAsyncTask(NsDao nsDao) {
            this.nsDao = nsDao;
        }

        @Override
        protected Void doInBackground(T... item) {

            nsDao.update(item[0]);
            return null;
        }
    }

    private static class UpdateItemWithCallbackAsyncTask<T> extends AsyncTask<T, Void, Void> {

        private NsDao<T> nsDao;
        private RDBCallback.SingleObjectCallback callback;


        public UpdateItemWithCallbackAsyncTask(NsDao nsDao, RDBCallback.SingleObjectCallback callback) {
            this.nsDao = nsDao;
            this.callback = callback;
        }

        @Override
        protected Void doInBackground(T... item) {

            nsDao.update(item[0]);
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {

            if (callback != null) {

                callback.onSuccess("");
            }
        }
    }


    private static class DeleteItemAsyncTask<T> extends AsyncTask<T, Void, Void> {

        private NsDao<T> nsDao;

        public DeleteItemAsyncTask(NsDao nsDao) {
            this.nsDao = nsDao;
        }

        @Override
        protected Void doInBackground(T... Items) {

            nsDao.delete(Items[0]);
            return null;
        }
    }


    private static class DeleteAllItemAsyncTask<T> extends AsyncTask<Object, Void, Void> {

        private NsDao<T> nsDao;

        public DeleteAllItemAsyncTask(NsDao nsDao) {
            this.nsDao = nsDao;
        }

        @Override
        protected Void doInBackground(Object... voids) {

            nsDao.deleteAll();
            return null;
        }
    }
}
