# Android-JsonParseWithVolley

## How to Parse a Json Using Volley
### SIMPLE GET REQUEST

https://api.myjson.com/bins/kp9wz

![](https://github.com/MyCroft06/Android-JsonParseWithVolley/blob/master/Volley.gif)

AndroidManifest.xml :
```
<uses-permission android:name="android.permission.INTERNET"/>
```

build.gradle :

```
implementation 'com.android.volley:volley:1.1.0'
```

MainActivity.java :

```
private RequestQueue mQueue;
 
mQueue = Volley.newRequestQueue(this);
jsonParse();
```
```
private void jsonParse(){
        String url = "https://api.myjson.com/bins/kp9wz";
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            JSONArray jsonArray = response.getJSONArray("employees");
                            txtView.setText("");
                            for (int i = 0; i <jsonArray.length(); i++){
                                JSONObject employee = jsonArray.getJSONObject(i);
                                String  firstName = employee.getString("firstname");
                                int  age = employee.getInt("age");
                                String  mail = employee.getString("mail");
                                txtView.append(firstName + "," + String.valueOf(age) + "," + mail + "\n\n");
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
            }
        });
     mQueue.add(request);
    }
```
