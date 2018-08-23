
package com.kwabenaberko.cointrack.di.modules;

        import android.app.Application;

        import javax.inject.Singleton;

        import dagger.Module;
        import dagger.Provides;

/**
 * Created by Kwabena Berko on 6/18/2018.
 */

@Module
public class AppModule {
    private Application mApplication;

    public AppModule(Application application){
        mApplication = application;
    }

    @Provides
    @Singleton
    Application provideApplication(){
        return mApplication;
    }
}


