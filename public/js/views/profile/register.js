/* global jQuery, ko, wallpaper:true */

var wallpaper = wallpaper || { };
wallpaper.profile = wallpaper.profile || { };
wallpaper.profile.register = wallpaper.profile.register || { };

wallpaper.profile.register.Model = (function ($, ko) {
	"use strict";

	var Model = function(data) {


		this.initialize(data);
	};

	ko.utils.extend(Model.prototype, {
		initialize: function(data) {

		}
	});

	return Model;
}(jQuery, ko));

(function (ko) {
	"use strict";

	var model = new wallpaper.profile.register.Model(wallpaper.profile.register.data);

	ko.applyBindings(model);
}(ko));
