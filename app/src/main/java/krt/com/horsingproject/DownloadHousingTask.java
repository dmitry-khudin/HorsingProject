package krt.com.horsingproject;

import android.inputmethodservice.Keyboard;
import android.os.AsyncTask;
import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;

/*
TODO:

1. Set the type parameters for this class.  This AsyncTask will not use progress.
   Its input will be a URL string, and its result will be a list of HousingProject
   objects.  The important fields in the CSV file are as follows:
   a. latitude (column 0, header says 'X')
   b. longitude (column 1, header says 'Y')
   c. address (column 5, header says 'PROJ_ADDRESS')
   d. municipality (column 6, header says 'MUNICIPALITY')
   e. numUnits (column 9, header says 'NUM_UNITS')
2. Implement the doInBackground() method to download and process the CSV data
   into a list of HousingProject objects.
3. Implement the onPostExecute() method to handle any exceptions and pass the
   list of HousingProjects back to the listener.
*/
public class DownloadHousingTask extends AsyncTask<String, String, List<String>> {
    private Exception exception = null;
    private HousingDownloadListener listener = null;

    public DownloadHousingTask(HousingDownloadListener listener) {
        this.listener = listener;
    }



    /**
	 * loadCSVLines()
	 *	
	 * @arg inStream The input stream from which to read the CSV data
	 *
	 * @return A list of strings, each of which will be one line of CSV data
	 *
	 * This function is included to help you process the CSV file.  This function
	 * downloads all of the data from the provided InputStream, and returns a list of
	 * lines.  Since we are downloading a CSV file, these lines will consist of 
	 * comma-separated data (like the example given in Listing 1).
	**/
    private List<String> loadCSVLines(InputStream inStream) throws IOException {
        List<String> lines = new ArrayList<>();

        BufferedReader in = new BufferedReader(new InputStreamReader(inStream));

        String line = null;
        while ((line = in.readLine()) != null) {
            lines.add(line);
        }

        return lines;
    }

	// TODO:  Implement the doInBackGround() method
	//        This method will download the CSV data from the URL
	//        parameter (params[0]), extract the relevant data from
	//        the file, creating a list of HousingProject objects.
	//        The HousingProject list will be the result.

    @Override
    protected List<String> doInBackground(String... params) {
        URL aURL = null;
        try {
            aURL = new URL(params[0]);
            URLConnection conn = aURL.openConnection();
            conn.connect();
            InputStream is = conn.getInputStream();
            return loadCSVLines(is);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }
    // TODO:  Implement the onPostExecute() method
    //        This method will handle exceptions, and pass the result data
    //        back to the listener


    @Override
    protected void onPostExecute(final List<String> strings) {
            super.onPostExecute(strings);
        List<HousingProject> list = new ArrayList<>();
        for (int i = 1; i < strings.size(); i++)
        {
            list.add(convertCSVToHorsingProject(strings.get(i)));
        }
        if (this.listener != null)
        {
            this.listener.housingDataDownloaded(list);
        }
    }

    HousingProject convertCSVToHorsingProject(String str)
    {
        String[] RowData = str.split(",");
        HousingProject project = new HousingProject(Float.valueOf(RowData[0]), Float.valueOf(RowData[1]), RowData[5], RowData[6], Integer.valueOf(RowData[9]));
        return project;
    }


}
