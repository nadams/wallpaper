/* global ko, wallpaper */

wallpaper.profile.Model = (function (ko) {
  "use strict";

  var Model = function(data) {
    this.initialize(data);
  };

  ko.utils.extend(Model.prototype, {
    initialize: function (data) {
      this.id = data.id;
      this.email = data.email;
      this.password = data.password;
      this.dateCreated = data.dateCreated;
    }
  });

  return Model;
}(ko));

(function (ko, wallpaper) {
  "use strict";

  var model = new wallpaper.profile.Model(wallpaper.profile.data);
  ko.applyBindings(model);
}(ko, wallpaper));
