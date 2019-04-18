package de.mpg.mpdl.ebooksreader.injection.module;

import dagger.Module;
import dagger.Provides;
import de.mpg.mpdl.ebooksreader.data.repository.EbooksRepository;
import de.mpg.mpdl.ebooksreader.data.repository.impl.EbooksRepositoryImpl;
import de.mpg.mpdl.ebooksreader.data.service.EbooksService;
import de.mpg.mpdl.ebooksreader.data.service.impl.EbooksServiceImpl;

@Module
public class EbooksModule {

    @Provides
    EbooksService provideEbooksService(EbooksServiceImpl service) {
        return service;
    }

    @Provides
    EbooksRepository provideEbooksRepository(EbooksRepositoryImpl repository) {
        return repository;
    }
}
