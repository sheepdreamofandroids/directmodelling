/**
 * Expose gwt model to angular.
 */

window.gwtStarted = function(init, calc) {
	console.log(init);
	angular.module('directDemo', [])
		.controller('directDemoController',
			[ '$scope', function($scope) {
				$scope.demo = init.getModel(); //window.demo;
				$scope.calculator = calc; //window.calc;

				$scope.signal = function(prop) {
					// turns signal into getterSetter
					return function(newVal) {
						if(angular.isDefined(newVal)) {
							prop.set(newVal);
						}
						return prop.get();
					}
				};
			} ]);

	//window.demo = model;
	//window.calc = calc;
	angular.bootstrap(document,['directDemo']);
};
