/* global jQuery, ko, wallpaper:true */

var wallpaper = wallpaper || { };
wallpaper.profile = wallpaper.profile || { };
wallpaper.profile.register = wallpaper.profile.register || { };

wallpaper.profile.register.Model = (function ($, ko) {
	"use strict";

	var Model = function(data) {
		this.usernameError = ko.observable();
		this.passwordError = ko.observable();
		this.passwordVerifyError = ko.observable();
		this.miscErrors = ko.observableArray([]);

		this.initialize(data);

		this.usernameHasError = ko.computed(function () {
			return this.valueExists(this.usernameError());
		}, this);

		this.passwordHasError = ko.computed(function () {
			return this.valueExists(this.passwordError());
		}, this);

		this.passwordVerifyHasError = ko.computed(function () {
			return this.valueExists(this.passwordVerifyError());
		}, this);
	};

	ko.utils.extend(Model.prototype, {
		initialize: function(data) {
			this.usernameError(data.usernameError);
			this.passwordError(data.passwordError);
			this.passwordVerifyError(data.passwordVerifyError);

			var that = this;

			_.each(data.miscErrors, function(error) {
				that.miscErrors.push(error);
			});
		},
		valueExists: function(value) {
			return value !== undefined && value.length > 0;
		}
	});

	return Model;
}(jQuery, ko));

(function (ko) {
	"use strict";

	var model = new wallpaper.profile.register.Model(wallpaper.profile.register.data.errors);

	ko.applyBindings(model);
}(ko));
