package utils;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import com.google.maps.DirectionsApi;
import com.google.maps.GeoApiContext;
import com.google.maps.GeocodingApi;
import com.google.maps.errors.OverQueryLimitException;
import com.google.maps.model.DirectionsRoute;
import com.google.maps.model.GeocodingResult;
import com.google.maps.model.LatLng;
import com.google.maps.model.TravelMode;

import beans.Address;
import beans.BeanClient;
import beans.CoordonateGps;
import beans.StareMasina;

public class MapsUtils {

	public static long distanceRealXtoY(double lat1, double lon1, double lat2, double lon2) throws Exception {

		GeoApiContext context = new GeoApiContext().setApiKey(Constants.GOOGLE_MAPS_API_KEY);

		LatLng start = new LatLng(lat1, lon1);
		LatLng stop = new LatLng(lat2, lon2);

		DirectionsRoute[] routes = DirectionsApi.newRequest(context).mode(TravelMode.DRIVING).origin(start).destination(stop).mode(TravelMode.DRIVING)
				.optimizeWaypoints(true).await();

		return routes[0].legs[0].distance.inMeters / 1000;

	}

	public static DirectionsRoute[] traseuBorderou(StareMasina stareMasina, Set<BeanClient> listClienti) {

		List<String> wayPoints = new ArrayList<>();
		String stopPoint = "";

		DirectionsRoute[] routes = null;

		try {

			// limitare 23 clienti!
			for (BeanClient client : listClienti) {

				if (Double.valueOf(client.getCoordGps().split(",")[0]) > 0) {
					wayPoints.add(client.getCoordGps());
					stopPoint = client.getCoordGps();
				}
			}

			String[] arrayPoints = wayPoints.toArray(new String[wayPoints.size()]);

			GeoApiContext context = new GeoApiContext().setApiKey(Constants.GOOGLE_MAPS_API_KEY);

			LatLng start = new LatLng(stareMasina.getCoordonateGps().getLatitude(), stareMasina.getCoordonateGps().getLongitude());
			LatLng stop = new LatLng(Double.valueOf(stopPoint.split(",")[0]), Double.valueOf(stopPoint.split(",")[1]));

			routes = DirectionsApi.newRequest(context).mode(TravelMode.DRIVING).origin(start).destination(stop).waypoints(arrayPoints).mode(TravelMode.DRIVING)
					.optimizeWaypoints(false).await();
		} catch (OverQueryLimitException q) {
			MailOperations.sendMail("sms: " + q.toString());
		} catch (Exception ex) {
			MailOperations.sendMail(" sms traseuBorderou: " + ex.toString());
		}

		return routes;

	}

	public static CoordonateGps geocodeAddress(Address address) {
		CoordonateGps coordonateGps = null;

		StringBuilder strAddress = new StringBuilder();

		try {

			if (address.getStreet() != null && !address.getStreet().equals("")) {
				strAddress.append(address.getStreet());
				strAddress.append(",");
			}

			if (address.getStrNumber() != null && !address.getStreet().equals("")) {
				strAddress.append(address.getStrNumber());
				strAddress.append(",");
			}

			if (address.getSector() != null && !address.getSector().equals("")) {
				strAddress.append(UtilsAdrese.getNumeJudet(address.getSector()));
				strAddress.append(",");
			}

			if (address.getCity() != null && !address.getCity().equals("")) {
				strAddress.append(address.getCity());
				strAddress.append(",");
			}

			strAddress.append(address.getCountry());

			GeoApiContext context = new GeoApiContext().setApiKey(Constants.GOOGLE_MAPS_API_KEY);
			GeocodingResult[] results = GeocodingApi.geocode(context, strAddress.toString()).await();

			double latitude = 0;
			double longitude = 0;

			if (results.length > 0) {
				latitude = results[0].geometry.location.lat;
				longitude = results[0].geometry.location.lng;
			}

			coordonateGps = new CoordonateGps(latitude, longitude);
		} catch (Exception ex) {
			MailOperations.sendMail("geocodeAddress: " + ex.toString());
		}
		return coordonateGps;
	}

	public static double distanceStraightXtoY(double lat1, double lon1, double lat2, double lon2, String unit) {

		if (lat1 == lat2 && lon1 == lon2)
			return 0;

		double theta = lon1 - lon2;
		double dist = Math.sin(deg2rad(lat1)) * Math.sin(deg2rad(lat2)) + Math.cos(deg2rad(lat1)) * Math.cos(deg2rad(lat2)) * Math.cos(deg2rad(theta));
		dist = Math.acos(dist);
		dist = rad2deg(dist);
		dist = dist * 60 * 1.1515;
		if (unit == "K") {
			dist = dist * 1.609344;
		} else if (unit == "N") {
			dist = dist * 0.8684;
		}
		return (dist);
	}

	private static double deg2rad(double deg) {
		return (deg * Math.PI / 180.0);
	}

	private static double rad2deg(double rad) {
		return (rad * 180 / Math.PI);
	}

	private static LatLng getCoordsBacGL() {
		return new LatLng(45.416883, 28.033634);
	}

	public static boolean isOverBac(LatLng coordsMasina) {

		if (distanceStraightXtoY(getCoordsBacGL().lat, getCoordsBacGL().lng, coordsMasina.lat, coordsMasina.lng, "K") > 8)
			return true;

		return false;

	}

}
