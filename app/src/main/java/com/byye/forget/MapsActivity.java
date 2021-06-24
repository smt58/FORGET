package com.byye.forget;

import android.Manifest;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;


import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionDeniedResponse;
import com.karumi.dexter.listener.PermissionGrantedResponse;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.single.PermissionListener;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentActivity;

import static com.byye.forget.MyLocationService.ilkadrx;
import static com.byye.forget.MyLocationService.ilkadry;


public class MapsActivity extends FragmentActivity implements OnMapReadyCallback, GoogleMap.OnMapLongClickListener{


    user user1 = new user();

    static MapsActivity instance;
    LocationRequest locationRequest;
    FusedLocationProviderClient fusedLocationProviderClient;
    TextView textView;
    TextView textView2;

    CardView button;


    Button button2;
    Button button3;



    SupportMapFragment mapFragment;

    public static MapsActivity getInstance() {
        return instance;

    }


    SearchView searchView; //yeni


    private GoogleMap mMap;
    LocationManager locationManager;
    LocationListener locationListener;

    public double  ilkkonumx,ilkkonumy;

    public double  knmx=0.0,knmy=0.0;
    public double  tıkknmx=0.0,tıkknmy=0.0;

    public static int adresunl;
    public static int tıklananyerise=0;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        button=findViewById(R.id.knmkaydet);
        searchView=findViewById(R.id.locationsv);
        button2=findViewById(R.id.plus2);
        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openDialog();
            }
        });

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        instance = this;
        textView=findViewById(R.id.txt_location);
        textView2=findViewById(R.id.textView2);



        button3=findViewById(R.id.plus3);





        //yenii
        //yenii
 /* searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                String location=searchView.getQuery().toString();
                List<Address> addressList =null;
                if(location!=null || location.equals("")){

                    Geocoder geocoder=new Geocoder(MapsActivity.this);
                    try {
                        addressList=geocoder.getFromLocationName(location,1);

                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                    Address adress=addressList.get(0);
                    LatLng latLng=new LatLng(adress.getLatitude(),adress.getLongitude());
                    //mMap.addMarker(new MarkerOptions().position(latLng).title(location));
                    mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng,15));

                }

                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                return false;
            }
        });

  */

      searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {

                String searchinitlocation=searchView.getQuery().toString();

                List<Address> addressList=null;

                if(searchinitlocation!=null && !searchinitlocation.equals(""))
                {
                    Geocoder geocoder=new Geocoder(MapsActivity.this);

                    try
                    {
                        addressList=geocoder.getFromLocationName(searchinitlocation,1);
                        Address address=addressList.get(0);
                        LatLng latLng=new LatLng(address.getLatitude(),address.getLongitude());
                        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng,10));
                    }
                    catch (Exception e)
                    {
                        e.printStackTrace();
                        Toast.makeText(getApplicationContext(),"ARADIĞINIZ KONUM BULUNAMADI",Toast.LENGTH_LONG).show();
                    }


                }


                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                return false;
            }
        });








        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //user1.setX_konum(ilkkonumx);

                Intent i=new Intent(MapsActivity.this,konum_baslik.class);

                if(tıkknmx!=0){
                    i.putExtra("enlem", tıkknmx);
                    i.putExtra("boylam",tıkknmy);

                }else{
                    i.putExtra("enlem", ilkadrx);   //yeni
                    i.putExtra("boylam",ilkadry);
                }


                startActivity(i);
                  finish();

            }
        });


        Dexter.withActivity(this)
                .withPermission(Manifest.permission.ACCESS_FINE_LOCATION)
                .withListener(new PermissionListener() {
                    @Override
                    public void onPermissionGranted(PermissionGrantedResponse response) {
                        updateLocation();

                    }

                    @Override
                    public void onPermissionDenied(PermissionDeniedResponse response) {

                        Toast.makeText(MapsActivity.this, "you must accept location", Toast.LENGTH_SHORT).show();

                    }

                    @Override
                    public void onPermissionRationaleShouldBeShown(PermissionRequest permission, PermissionToken token) {

                    }
                }).check();


    }



    public void openDialog() {
        ExampleDialog exampleDialog = new ExampleDialog();
        exampleDialog.show(getSupportFragmentManager(), "example dialog");
    }
    //benimliker

    private void updateLocation() {
        buildLocationRequest();
        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

            return;
        }
        fusedLocationProviderClient.requestLocationUpdates(locationRequest, getPendingIntent());
    }

    private PendingIntent getPendingIntent() {
        Intent intent=new Intent(this,MyLocationService.class);
        getIntent().setAction(MyLocationService.ACTION_PROCESS_UPDATE);
        return PendingIntent.getBroadcast(this,0,intent,PendingIntent.FLAG_UPDATE_CURRENT);
    }


    private void buildLocationRequest() {
        locationRequest =new LocationRequest();
        locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        locationRequest.setInterval(5000);
        locationRequest.setFastestInterval(3000);
        locationRequest.setSmallestDisplacement(20f);
        //10metre de arka planda toast mesajı gönderiyor



    }

    public  void updatetextwiew(final String value){
        //konumsaat();


        MapsActivity.this.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                textView.setText(value);

            }



        });




    }

    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        locationManager = (LocationManager) this.getSystemService(Context.LOCATION_SERVICE);

        locationListener = new LocationListener() {
            @Override
            public void onLocationChanged(Location location) {
                //mMap.clear();
                //LatLng userLocation = new LatLng(location.getLatitude(),location.getLongitude());
               // mMap.addMarker(new MarkerOptions().position(userLocation).title("Şu Anki Konumunuz"));
                //mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(userLocation,10));


                knmx=location.getLatitude();
                knmy=location.getLongitude();

                Geocoder geocoder=new Geocoder(getApplicationContext(), Locale.getDefault());



            }

            @Override
            public void onStatusChanged(String s, int i, Bundle bundle) {

            }

            @Override
            public void onProviderEnabled(String s) {

            }

            @Override
            public void onProviderDisabled(String s) {

            }
        };




   if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[] {Manifest.permission.ACCESS_FINE_LOCATION},1);
        } else {
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER,10000,40,locationListener);  //metre ve milisaniye cinsinden alıyor

        }

        button3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mMap.clear();
                if(ilkadrx!=0){
                    LatLng userLocation = new LatLng(ilkadrx,ilkadry);
                    mMap.addMarker(new MarkerOptions().position(userLocation).title("Şu Anki yer Konumunuz"));
                    mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(userLocation,10));

                }else{
                    textView.setText("KONUM BİLGİSİ YÜKLENEMEDİ TEKRAR DENEYİNİZ");
                }



            }
        });

// Add a marker in Sydney and move the camera
      /*  if (Build.VERSION.SDK_INT < 23) {
            LatLng sydney = new LatLng(39.9030, 32.4825);
            //mMap.addMarker(new MarkerOptions().position(sydney).title("Marker in YOZGAT"));
            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(sydney, 7));

        }
       // LatLng sydney = new LatLng(39.7930348, 35.0227466);
        //mMap.addMarker(new MarkerOptions().position(sydney).title("Marker in YOZGAT"));
        //mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(sydney, 10));
*/
      /*  if (Build.VERSION.SDK_INT >= 23) {
            if (checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                requestPermissions(new String[] {Manifest.permission.ACCESS_FINE_LOCATION},1);
            } else {
                locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER,10000,30,locationListener);

                Location lastLocation = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
                System.out.println("lastLocation: " + lastLocation);
                LatLng userLastLocation = new LatLng(lastLocation.getLatitude(),lastLocation.getLongitude());
                mMap.addMarker(new MarkerOptions().title("Your Location").position(userLastLocation));
                mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(userLastLocation,15));
            }
        } else {
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER,10000,30,locationListener);

            Location lastLocation = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
            System.out.println("lastLocation: " + lastLocation);
            LatLng userLastLocation = new LatLng(lastLocation.getLatitude(),lastLocation.getLongitude());
            mMap.addMarker(new MarkerOptions().title("Your Location").position(userLastLocation));
            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(userLastLocation,15));
        }*/

        mMap.setOnMapLongClickListener(this);





    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {

        if (grantResults.length > 0) {
            if (requestCode == 1) {
                if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
                    locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER,0,0,locationListener);
                }
            }
        }

        super.onRequestPermissionsResult(requestCode, permissions, grantResults);


    }


    @Override
    public void onMapLongClick(LatLng latLng) {   //haritaya uzunca tıklanınca konum kaydetmesi

        mMap.clear();

        Geocoder geocoder = new Geocoder(getApplicationContext(), Locale.getDefault());

        String address = "";

        try {
            List<Address> addressList = geocoder.getFromLocation(latLng.latitude,latLng.longitude,1);
            if (addressList != null && addressList.size() > 0) {
                if (addressList.get(0).getThoroughfare() != null) {
                    address += addressList.get(0).getThoroughfare();

                    if (addressList.get(0).getSubThoroughfare() != null) {
                        address += addressList.get(0).getSubThoroughfare();
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        if (address.matches("")) {
            address = "No Address";
        }



        mMap.addMarker(new MarkerOptions().position(latLng).title(address));   //uzunca tıklanan yere marker ekliyor
        System.out.println("tık adrs"+latLng.latitude+" "+latLng.longitude);  //tıklanan yani gidilecek olan yerin adresleri

        tıklananyerise=2;
        tıkknmx=latLng.latitude;  //yeni
        tıkknmy=latLng.longitude;

        Toast.makeText(getApplicationContext(),R.string.yeniyer,Toast.LENGTH_SHORT).show();




    }

    @Override
    public void onBackPressed() {
        Intent intent=new Intent(MapsActivity.this,ara_menu.class);
        startActivity(intent);
        finish();

    }



}