var exec = require('cordova/exec');

// exports.coolMethod = function (arg0, success, error) {
//     exec(success, error, 'GreatDayPlugin', 'coolMethod', [arg0]);
// };

module.exports = {
  getLocation: function (onSuccess, onError) {
    exec(onSuccess, onError, 'GreatDayPlugin', 'getLocation', []);
  },
  getCamera: function (arg0, onSuccess, onError) {
    exec(onSuccess, onError, 'GreatDayPlugin', 'getCamera', [arg0]);
  },
  getCameraSwap: function (arg0, onSuccess, onError) {
    exec(onSuccess, onError, 'GreatDayPlugin', 'getCameraSwap', [arg0]);
  },
  getLocationCamera: function (arg0, onSuccess, onError) {
    exec(onSuccess, onError, 'GreatDayPlugin', 'getLocationCamera', [arg0]);
  }
};
