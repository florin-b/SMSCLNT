package utils;

import com.google.maps.DirectionsApi;
import com.google.maps.GeoApiContext;
import com.google.maps.GeocodingApi;
import com.google.maps.model.DirectionsRoute;
import com.google.maps.model.GeocodingResult;
import com.google.maps.model.LatLng;
import com.google.maps.model.TravelMode;

import beans.Address;
import beans.CoordonateGps;

public class MapsUtils {

	public static long distanceRealXtoY(double lat1, double lon1, double lat2, double lon2) throws Exception {

		GeoApiContext context = new GeoApiContext().setApiKey(Constants.GOOGLE_MAPS_API_KEY);

		LatLng start = new LatLng(lat1, lon1);
		LatLng stop = new LatLng(lat2, lon2);

		DirectionsRoute[] routes = DirectionsApi.newRequest(context).mode(TravelMode.DRIVING).origin(start).destination(stop).mode(TravelMode.DRIVING)
				.optimizeWaypoints(true).await();

		return routes[0].legs[0].distance.inMeters;

	}

	public static CoordonateGps geocodeAddress(Address address) throws Exception {
		CoordonateGps coordonateGps = null;

		StringBuilder strAddress = new StringBuilder();

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

		double latitude = results[0].geometry.location.lat;
		double longitude = results[0].geometry.location.lng;

		coordonateGps = new CoordonateGps(latitude, longitude);

		return coordonateGps;
	}

	public static double distanceStraightXtoY(double lat1, double lon1, double lat2, double lon2, String unit) {
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

}
