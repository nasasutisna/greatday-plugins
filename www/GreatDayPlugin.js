var exec = require('cordova/exec');

// exports.coolMethod = function (arg0, success, error) {
//     exec(success, error, 'GreatDayPlugin', 'coolMethod', [arg0]);
// };

module.exports = {
  /**
   * Get Camera
   * @param {*} photoName 
   * @param {*} quality 
   * @param {*} maxSize 
   * @param {*} onSuccess 
   * @param {*} onError 
   */
  getCamera: function (photoName, quality, maxSize, onSuccess, onError) {
    var data = [];
    data.push({
      photoName: photoName,
      quality: quality,
      max_size: maxSize
    });
    exec(onSuccess, onError, 'GreatDayPlugin', 'getCamera', data);
  },

  /**
   *  Get camera with swap on or off
   * @param {*} photoName 
   * @param {*} quality 
   * @param {*} maxSize 
   * @param {*} onSuccess 
   * @param {*} onError 
   */
  getCameraSwap: function (photoName, quality, maxSize, onSuccess, onError) {
    var data = [];
    data.push({
      photoName: photoName,
      quality: quality,
      max_size: maxSize
    });
    exec(onSuccess, onError, 'GreatDayPlugin', 'getCameraSwap', data);
  },

  /**
   * Just Get Location
   */
  getLocation: function (onSuccess, onError) {
    exec(onSuccess, onError, 'GreatDayPlugin', 'getLocation', []);
  },

  /*
  * get location with radius
  * arg0 = String ("{data:[{work_lat: -6.202394, work_lon: 106.652710, work_radius: 1000 },{work_lat: -6.175110, work_lon: 106.865036, work_radius: 1000 }]}")
  */
  getLocationRadius: function (arg0, onSuccess, onError) {
    exec(onSuccess, onError, 'GreatDayPlugin', 'getLocationRadius', [arg0]);
  },

  /**
   * get location and camera
   * @param {*} photoName 
   * @param {*} quality 
   * @param {*} maxSize 
   * @param {*} onSuccess 
   * @param {*} onError 
   */
  getLocationCamera: function (photoName, quality, maxSize, onSuccess, onError) {
    var data = [];
    data.push({
      photoName: photoName,
      quality: quality,
      max_size: maxSize
    });
    exec(onSuccess, onError, 'GreatDayPlugin', 'getLocationCamera', data);
  },

  /**
   * get Location and camera swap on/off
   * @param {*} photoName 
   * @param {*} quality 
   * @param {*} maxSize 
   * @param {*} onSuccess 
   * @param {*} onError 
   */
  getLocationCameraSwap: function (photoName, quality, maxSize, onSuccess, onError) {
    var data = [];
    data.push({
      photoName: photoName,
      quality: quality,
      max_size: maxSize
    });

    exec(onSuccess, onError, 'GreatDayPlugin', 'getLocationCameraSwap', data);
  },

  /**
   * get Location with radius and camera
   * photo: String (name photo)
   * location: String "{data:[{work_lat: -6.202394, work_lon: 106.652710, work_radius: 1000 },{work_lat: -6.175110, work_lon: 106.865036, work_radius: 1000 }]}"
   * @param {*} photoName 
   * @param {*} quality 
   * @param {*} maxSize 
   * @param {*} location 
   * @param {*} onSuccess 
   * @param {*} onError 
   */
  getLocationRadiusCamera: function (photoName, quality, maxSize, location, onSuccess, onError) {
    var data = [];
    data.push({
      photoName: photoName,
      location: location,
      quality: quality,
      max_size: maxSize
    });

    exec(onSuccess, onError, 'GreatDayPlugin', 'getLocationRadiusCamera', data);
  },

  /**
   * get Location with radius and camera swap on/off    
   * photo: String (name photo)
   * location: String "{data:[{work_lat: -6.202394, work_lon: 106.652710, work_radius: 1000 },{work_lat: -6.175110, work_lon: 106.865036, work_radius: 1000 }]}" 
   * @param {*} photoName 
   * @param {*} quality 
   * @param {*} maxSize 
   * @param {*} location 
   * @param {*} onSuccess 
   * @param {*} onError 
   */
  getLocationRadiusCameraSwap: function (photoName, quality, maxSize, location, onSuccess, onError) {
    var data = [];
    data.push({
      photoName: photoName,
      location: location,
      quality: quality,
      max_size: maxSize
    })
    exec(onSuccess, onError, 'GreatDayPlugin', 'getLocationRadiusCameraSwap', data);
  },

  /**
   * Get Location with label and language
   * photo: String (name photo)
   * photo: String (name photo)
   * Language: 
   *  indo = in
   *  jepang = ja
   *  korea = ko
   *  thailang = th
   *  china = zh
   */
  getLocationLabelLanguage: function (label1, label2, language, onSuccess, onError) {
    var data = [];
    data.push({
      label1: label1,
      label2: label2,
      language: language
    })
    exec(onSuccess, onError, 'GreatDayPlugin', 'getLocationLabelLanguage', data);
  },

  /**
   * Get Location with label and language
   * photo: String (name photo)
   * photo: String (name photo)
   * Language: 
   *  indo = in
   *  jepang = ja
   *  korea = ko
   *  thailang = th
   *  china = zh
   * location: String "{data:[{work_lat: -6.202394, work_lon: 106.652710, work_radius: 1000 },{work_lat: -6.175110, work_lon: 106.865036, work_radius: 1000 }]}"
   */
  getLocationLabelLanguageRadius: function (label1, label2, language, location, onSuccess, onError) {
    var data = [];
    data.push({
      label1: label1,
      label2: label2,
      language: language,
      location: location
    })
    exec(onSuccess, onError, 'GreatDayPlugin', 'getLocationLabelLanguageRadius', data);
  }
};
