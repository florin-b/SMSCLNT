package main;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import com.google.maps.DirectionsApi;
import com.google.maps.GeoApiContext;
import com.google.maps.model.DirectionsRoute;
import com.google.maps.model.LatLng;
import com.google.maps.model.TravelMode;

import utils.Constants;

public class MainTest {

	public static void main(String[] args) {

		try {
			// testApi();

			System.out.println(isTimeToSmsAlert());

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public static boolean isTimeToSmsAlert() {
		Date dateNow = new Date();

		Calendar cal = Calendar.getInstance();
		cal.set(Calendar.HOUR_OF_DAY, 07);
		cal.set(Calendar.MINUTE, 30);

		Date dateMin = cal.getTime();

		return dateNow.after(dateMin);
	}

	private static void testApi() throws Exception {

		GeoApiContext context = new GeoApiContext().setApiKey(Constants.GOOGLE_MAPS_API_KEY);

		LatLng start = new LatLng(45.425265, 28.005572);
		LatLng stop = new LatLng(45.424277, 28.006394);
		String[] points = { "44.7986627,28.8888885", "45.3255111,28.1953217", "45.03703489999999,29.1617668", "45.17792730000001,28.7666267",
				"45.1773216,28.7765729", "45.4242767,28.006394" };

		DirectionsRoute[] routes = DirectionsApi.newRequest(context).mode(TravelMode.DRIVING).origin(start).destination(stop).waypoints(points)
				.mode(TravelMode.DRIVING).optimizeWaypoints(false).await();

		System.out.println(routes[0].legs[0].distance.inMeters);

	}

}
