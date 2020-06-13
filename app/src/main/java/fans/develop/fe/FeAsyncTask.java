package fans.develop.fe;

import android.os.AsyncTask;

public class FeAsyncTask<U> extends AsyncTask<Integer, Integer, String> {

    // 第一个参数：传入 doInBackground() 方法的参数类型
    // 第二个参数：传入 onProgressUpdate() 方法的参数类型
    // 第三个参数：传入 onPostExecute() 方法的参数类型，也是 doInBackground() 方法返回的类型

    public FeAsyncTask(U data){

    }

    // 方法1：onPreExecute（）
    // 作用：执行 线程任务前的操作
    // 注：根据需求复写
    @Override
    protected void onPreExecute() {
        // UI操作
        ;
    }

    // 方法2：doInBackground（）
    // 作用：接收输入参数、执行任务中的耗时操作、返回 线程任务执行的结果
    // 注：必须复写，从而自定义线程任务
    @Override
    protected String doInBackground(Integer... integers) {
        // 后台操作
        try {

            // 自定义的线程任务
            ;

            // 通知 onProgressUpdate（）
            publishProgress(99);

            Thread.sleep(1000);

        }catch (java.lang.InterruptedException e){

        }

        return ""; // 跳转到 onPostExecute()
    }

    // 方法3：onProgressUpdate（）
    // 作用：在主线程 显示线程任务执行的进度
    // 注：根据需求复写
    @Override
    protected void onProgressUpdate(Integer... values) {
        // UI操作
        ;
    }

    // 方法4：onPostExecute（）
    // 作用：接收线程任务执行结果、将执行结果显示到UI组件
    // 注：必须复写，从而自定义UI操作
    @Override
    protected void onPostExecute(String result) {
        // UI操作
        ;
    }

    // 方法5：onCancelled()
    // 作用：将异步任务设置为：取消状态
    @Override
    protected void onCancelled() {
        // UI操作
        ;
    }
}

/* ***************************************************************************

    // 步骤1：创建 AsyncTask 子类的实例对象（即 任务实例）
    // 注：AsyncTask 子类的实例必须在UI线程中创建
    FeAsyncTask fat = new FeAsyncTask();

    // 步骤2：手动调用 execute(Params... params) 从而执行异步线程任务
    // 注：
    //   a. 必须在UI线程中调用
    //   b. 同一个 AsyncTask 实例对象只能执行1次，若执行第2次将会抛出异常
    //   c. 执行任务中，系统会自动调用 AsyncTask 的一系列方法：onPreExecute() 、doInBackground()、onProgressUpdate() 、onPostExecute()
    //   d. 不能手动调用上述方法
    fat.execute(params);

*************************************************************************** */
