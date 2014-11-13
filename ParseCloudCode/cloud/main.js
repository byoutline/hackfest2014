
// Use Parse.Cloud.define to define as many cloud functions as you want.
// For example:
Parse.Cloud.define("hello", function(request, response) {
  response.success("Hello world!");
});
Parse.Cloud.beforeSave("SteamUser", function(request, response) {
Parse.Cloud.httpRequest({
  url: 'http://steamcommunity.com/id/'+ request.object.get("nickname"),
  params: {
    xml : 1
  },
  success: function(httpResponse) {
    var resp = httpResponse.text;
    var regex =/<steamID64>(\d*)<\/steamID64>/g;
    
    var steamid =regex.exec(resp)[1];
    console.log(httpResponse.text);
console.log(steamid);
    request.object.set("steamid", steamid);
    response.success();
  },
  error: function(httpResponse) {
    console.error('Request failed with response code ' + httpResponse.status);
    response.error("error");
  }
});
});
