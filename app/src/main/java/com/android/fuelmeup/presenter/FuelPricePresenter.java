package com.android.fuelmeup.presenter;

import android.content.Context;

import android.location.Address;
import android.location.Geocoder;
import android.location.Location;

import com.android.fuelmeup.dagger.DaggerInjector;
import com.android.fuelmeup.model.CityCode;
import com.android.fuelmeup.model.DatabaseHandler;
import com.android.fuelmeup.model.DieselPriceService;
import com.android.fuelmeup.model.FuelPrice;
import com.android.fuelmeup.model.PetrolPriceService;
import com.android.fuelmeup.model.FuelStation;
import com.android.fuelmeup.utils.LocationUtils;
import com.android.fuelmeup.view.FuelPriceViewInterface;
import com.google.android.gms.maps.model.LatLng;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import javax.inject.Inject;

import okhttp3.ResponseBody;
import rx.Observable;
import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by Vishal on 25-11-2017.
 */

public class FuelPricePresenter extends BasePresenter {
    private Context mContext;
    @Inject
    PetrolPriceService petrolPriceService;
    @Inject
    DieselPriceService dieselPriceService;
    private FuelPriceViewInterface viewInterface;
    public FuelPricePresenter(FuelPriceViewInterface viewInterface){
        this.viewInterface = viewInterface;
    }
    public void getNearestCity(Location location, Context context){
        mContext = context;
        DaggerInjector.getApiComponent().inject(this);
        String cityName = getLocationName(location.getLatitude(), location.getLongitude());
        DatabaseHandler dbHandler = new DatabaseHandler(mContext);
        ArrayList<CityCode> codes = dbHandler.getCityCodes(cityName);
        if(codes.size() >0) {
            CityCode nearestCity = getNearestCityCode(codes, location);
            getFuelPrices(nearestCity);
        }
    }

    private void getFuelPrices(final CityCode nearestCity) {
        Observable<ResponseBody> petrolRequest = petrolPriceService.getFuelPrice(nearestCity.code);
        petrolRequest.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<ResponseBody>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(ResponseBody responseBody) {
                        try {
                            String str = responseBody.string();
                            FuelPrice fuelPrice = fuelPriceFromHtml(str, FuelPrice.TYPE_PETROL,
                                    nearestCity.cityName, nearestCity.code);
                            viewInterface.onPetrolPriceSuccess(fuelPrice);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }

                    }
                });
        Observable<ResponseBody> dieselRequest = dieselPriceService.getFuelPrice(nearestCity.code);
        dieselRequest.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<ResponseBody>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(ResponseBody responseBody) {
                        try {
                            String str = responseBody.string();
                            FuelPrice fuelPrice = fuelPriceFromHtml(str, FuelPrice.TYPE_DIESEL,
                                    nearestCity.cityName, nearestCity.code);
                            viewInterface.onDieselPriceSuccess(fuelPrice);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }

                    }
                });
    }

    private FuelPrice fuelPriceFromHtml(String str, String fuelType, String cityName, String cityCode) {
        Document doc = Jsoup.parse(str);
        Elements priceElement = doc.select("input[id$=CPH1_hdnCurrPrice]");
        Elements dateElement = doc.select("input[id$=CPH1_hdnChangeDate]");
        String price = priceElement.attr("value");
        String date = dateElement.attr("value");
        FuelPrice fuelPrice = new FuelPrice(fuelType, cityName, cityCode);
        fuelPrice.setTodayPrice(date, price);

        Elements pastPricesElements = doc.select("div[class$=AlignInLine]");
        ArrayList<FuelPrice.DatePrice> pastPrices = new ArrayList<>();
        for(Element element : pastPricesElements){
            if(element.hasAttr("class")){
                Elements pastPriceElement = element.select("span[class$=price]");
                String pastPrice = pastPriceElement.text();
                Elements pastDateElement = element.select("span[class$=alignment]");
                String pastDate = pastDateElement.text();
                pastPrices.add(fuelPrice.datePrice(pastDate.replace(
                        "Most Recent price change date: ", "")
                                .replace("As on: ", "")
                                .replace("(", "")
                                .replace(")", ""),
                        pastPrice.split(" = ")[1].replace("Rs/Ltr", "")));
            }
        }
        fuelPrice.setPastPrices(pastPrices);
        return fuelPrice;
    }

    private CityCode getNearestCityCode(ArrayList<CityCode> codes, Location currentLocation) {
        CityCode nearestCity = codes.get(0);
        int distance = 0;
        for(CityCode code:codes){
            LatLng currentLatLng = new LatLng(currentLocation.getLatitude(),
                    currentLocation.getLongitude());
            String[] cityLatLngStr = code.latLng.split(",");
            LatLng cityLatLng = new LatLng(Double.parseDouble(cityLatLngStr[0]),
                    Double.parseDouble(cityLatLngStr[1]));
            int tempDistance = FuelStation.calculateDistance(currentLatLng, cityLatLng);
            if(distance == 0){
                distance = tempDistance;
            } else {
                if(tempDistance < distance)
                    nearestCity = code;
            }
        }
        return nearestCity;
    }

    public String getLocationName(double latitude, double longitude){
        String cityName = "Not Found";
        Geocoder gcd = new Geocoder(mContext, Locale.getDefault());
        try {
            List<Address> addresses = gcd.getFromLocation(latitude, longitude,10);
            for (Address adrs : addresses) {
                if (adrs != null) {
                    String city = adrs.getLocality();
                    if (city != null && !city.equals("")) {
                        cityName = city;
                        break;
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return cityName;
    }
}
