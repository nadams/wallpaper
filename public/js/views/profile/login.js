/* global ko, _, wallpaper:true */

var wallpaper = wallpaper || { };
wallpaper.profile = wallpaper.profile || { };
wallpaper.profile.login = wallpaper.profile.login || { };

wallpaper.profile.login.Model = (function (ko, _) {
  "use strict";

  var Model = function(data) {
    this.emailError = ko.observable();
    this.passwordError = ko.observable();
    this.formErrors = ko.observableArray();

    this.initialize(data);

    this.emailHasError = ko.computed(function() {
      return this.valueExists(this.emailError());
    }, this);

    this.passwordHasError = ko.computed(function() {
      return this.valueExists(this.passwordError());
    }, this);
  };

  ko.utils.extend(Model.prototype, {
    initialize: function(data) {
      this.emailError(data.emailError);
      this.passwordError(data.passwordError);

      var that = this;

      _.each(data.formErrors, function(error) {
        that.formErrors.push(error);
      });
    },
    valueExists: function(value) {
      return value !== undefined && value.length > 0;
    }
  });

  return Model;
}(ko, _));

(function (ko, wallpaper) {
  "use strict";

  var model = new wallpaper.profile.login.Model(wallpaper.profile.login.data.errors);

  ko.applyBindings(model);
}(ko, wallpaper));
