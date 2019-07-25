var exec = require('cordova/exec');

// exports.coolMethod = function (arg0, success, error) {
//     exec(success, error, 'GreatDayPlugin', 'coolMethod', [arg0]);
// };

module.exports = {
  getLocation: function(onSuccess, onError) {
      exec(onSuccess, onError, 'GreatDayPlugin', 'getLocation', []);
  }
};
