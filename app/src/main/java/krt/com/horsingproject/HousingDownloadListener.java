package krt.com.horsingproject;

import java.util.List;

public interface HousingDownloadListener {
    void housingDataDownloaded(List<HousingProject> housingProjects);
}
