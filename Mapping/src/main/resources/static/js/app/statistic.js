const app = angular.module('app', ['ngRoute']);
const serverIO = 'http://localhost:8080';
const pathIO = 'rest/statistic';

app.config(function ($routeProvider) {
    $routeProvider.when("/home", {
        templateUrl : "./home.htm"
    }).when("/CBA", {
        templateUrl : "./statisticCBA.htm", controller: 'control_CBA'
    }).when("/CBT", {
        templateUrl : "./statisticCBT.htm", controller: 'control_CBT'
    }).when("/LBA", {
        templateUrl : "./statisticLBA.htm", controller: 'control_LBA'
    }).when("/LBC", {
        templateUrl : "./statisticLBC.htm", controller: 'control_LBC'
    }).when("/LBT", {
        templateUrl : "./statisticLBT.htm", controller: 'control_LBT'
    }).otherwise({
        redirectTo: "/home"
    });
});

app.controller('control', function ($http, $scope) {

    console.log('A');
});

// THỐNG KÊ NỘI DUNG TẢI LÊN THEO TÀI KHOẢN
app.controller('control_CBA', function ($http, $scope) {

    // onload filter
    $http.get(getLink(serverIO,pathIO,'cs?t=MIN_MAX')).then(r => {
        $scope.fil = r.data[0];
        $scope.fil.end = new Date($scope.fil.et);
        $scope.fil.start = new Date($scope.fil.st);
        $scope.chart($scope.fil);
    }).catch(e => console.error(e))

    // show data with new chartJS
    $scope.chart = function(fil) {
        if(!fil) fil=$scope.fil;

        let qty = fil.qty ? fil.qty : 15; // số lượng
        let st = moment(fil.start).format('Y-MM-DD hh:mm:ss'); // start date
        let et = moment(fil.end).format('Y-MM-DD hh:mm:ss'); // end date
        let desc = fil.desc; // order by quantity
        let path = `cs?t=CBA&p=${qty}&p=${st}&p=${et+(desc!=undefined?`&p=${desc}`:'')}`;
        
        $http.get(getLink(serverIO,pathIO,path)
        ).then(r => {
            if(!r.data) return;

            var colors = [];
            var borders = [];
            var options = {
                scales: {
                    y: {
                        beginAtZero: true
                    }
                }
            };
            var datasets = [
                {
                    label: 'Nội dung đã tải',
                    data: r.data.map(x => x['quantity']),
                    backgroundColor: colors,
                    borderColor: borders,
                    borderRadius: 3,
                    borderWidth: 1
                }
            ];

            randomColor(r.data.length, colors, borders);
            chartSet('bar', r.data.map(e=>e.name), options, ...datasets)
        }).catch(e => console.error(e))
    }
});

// THỐNG KÊ NỘI DUNG TẢI LÊN THEO THỜI GIAN
app.controller('control_CBT', function ($http, $scope) {

    // onload filter
    $http.get(getLink(serverIO,pathIO,'cs?t=MIN_MAX')).then(r => {
        $scope.fil = r.data[0];
        $scope.fil.end = new Date($scope.fil.et);
        $scope.fil.start = new Date($scope.fil.st);
        $scope.chart($scope.fil);
    }).catch(e => console.error(e))

    // show data with new chartJS
    $scope.chart = function(fil) {
        if(!fil) fil=$scope.fil;

        let st = moment(fil.start).format('Y-MM-DD hh:mm:ss'); // start date
        let et = moment(fil.end).format('Y-MM-DD hh:mm:ss'); // end date
        let path = `cs?t=CBT&p=${fil.t ? fil.t : 1}&p=${st}&p=${et}`;
        
        $http.get(getLink(serverIO,pathIO,path)
        ).then(r => {
            if (!r.data) return;
            // prepare data to config
            var colors = [];
            var borders = [];
            var options = {
                responsive: true,
                plugins: {
                    title: {
                        display: true,
                        text: 'Nội dung được tải'
                    },
                },
                interaction: {
                    intersect: false,
                },
                scales: {
                    x: {
                        display: true,
                        title: {
                            display: true
                        }
                    },
                    y: {
                        display: true,
                        title: {
                            display: true,
                            text: 'Value'
                        },
                        suggestedMin: -1,
                        suggestedMax: Math.max(...r.data) + 1
                    }
                }
            };
            var datasets = [
                {
                    label: 'Nội dung đã tải',
                    data: r.data.map(x => x['quantity']),
                    backgroundColor: colors,
                    borderColor: borders,
                    borderRadius: 3,
                    pointRadius: 5,
                    pointHoverRadius: 10,
                    cubicInterpolationMode: 'monotone',
                    borderWidth: 1
                }, {
                    label: 'Dự kiến',
                    data: r.data.map(x => x['quantity']+.5),
                    backgroundColor: colors,
                    borderColor: '#0079ff39',
                    cubicInterpolationMode: 'monotone',
                    borderWidth: 3,
                    type: 'line'
                }
            ];
            
            randomColor(r.data.length, colors, borders);
            chartSet('bar', r.data.map(e => e.about), options, ...datasets)
        }).catch(e => console.error(e))
    }

});

// THỐNG KÊ LƯỢT THÍCH THEO TÀI KHOẢN
app.controller('control_LBA', function ($http, $scope) {

    // onload filter
    $http.get(getLink(serverIO,pathIO,'ls?t=MIN_MAX')).then(r => {
        $scope.fil = r.data[0];
        $scope.fil.qty = 15;
        $scope.fil.end = new Date($scope.fil.et);
        $scope.fil.start = new Date($scope.fil.st);
        $scope.chart($scope.fil);
    }).catch(e => console.error(e))

    // show data with new chartJS
    $scope.chart = function(fil) {
        if(!fil) fil=$scope.fil;

        let qty = fil.qty ? fil.qty : fil.length; // số lượng
        let st = moment(fil.start).format('Y-MM-DD hh:mm:ss'); // start date
        let et = moment(fil.end).format('Y-MM-DD hh:mm:ss'); // end date
        let path = `ls?t=LBA&p=${qty}&p=${st}&p=${et}`;
        
        $http.get(getLink(serverIO,pathIO,path)
        ).then(r => {
            if (!r.data) return;

            var colors = [];
            var borders = [];
            var options = {
                scales: {
                    y: {
                        beginAtZero: true
                    }
                }
            };
            var datasets = [
                {
                    label: 'Tài khoản thích nội dung',
                    data: r.data.map(x => x['quantity']),
                    backgroundColor: colors,
                    borderColor: borders,
                    borderRadius: 3,
                    borderWidth: 1
                }
            ];

            randomColor(r.data.length, colors, borders);
            chartSet('bar', r.data.map(e => e.name), options, ...datasets)
        }).catch(e => console.error(e))
    }

});

// THỐNG KÊ LƯỢT THÍCH THEO TÀI KHOẢN
app.controller('control_LBC', function ($http, $scope) {

    // onload filter
    $http.get(getLink(serverIO,pathIO,'ls?t=MIN_MAX')).then(r => {
        $scope.fil = r.data[0];
        $scope.fil.qty = 30;
        $scope.fil.end = new Date($scope.fil.et);
        $scope.fil.start = new Date($scope.fil.st);
        $scope.chart($scope.fil);
    }).catch(e => console.error(e))

    // show data with new chartJS
    $scope.chart = function(fil) {
        if(!fil) fil=$scope.fil;

        let qty = fil.qty ? fil.qty : fil.length; // số lượng
        let st = moment(fil.start).format('Y-MM-DD hh:mm:ss'); // start date
        let et = moment(fil.end).format('Y-MM-DD hh:mm:ss'); // end date
        let path = `ls?t=LBC&p=${qty}&p=${st}&p=${et}`;
        $http.get(getLink(serverIO,pathIO,path)).then(r => {
            if (!r.data) return;

            var colors = [];
            var borders = [];
            var options = {
                scales: {
                    y: {
                        beginAtZero: true
                    }
                }
            };
            var datasets = [
                {
                    label: 'Nội dung được thích',
                    data: r.data.map(x => x['quantity']),
                    backgroundColor: colors,
                    borderColor: borders,
                    borderRadius: 3,
                    borderWidth: 1
                }
            ];
            
            randomColor(r.data.length, colors, borders)
            chartSet('bar', r.data.map(e => e.subject), options, ...datasets)
        }).catch(e => console.error(e))
    }

});

// THỐNG KÊ LƯỢT THÍCH THEO TÀI KHOẢN
app.controller('control_LBT', function ($http, $scope) {

    // onload filter
    $http.get(getLink(serverIO,pathIO,'ls?t=MIN_MAX')).then(r => {
        $scope.fil = r.data[0];
        $scope.fil.t = 2;
        $scope.fil.end = new Date($scope.fil.et);
        $scope.fil.start = new Date($scope.fil.st);
        $scope.chart($scope.fil);
    }).catch(e => console.error(e))

    // show data with new chartJS
    $scope.chart = function(fil) {
        if(!fil) fil=$scope.fil;

        let qty = fil.qty ? fil.qty : fil.length; // số lượng
        let st = moment(fil.start).format('Y-MM-DD hh:mm:ss'); // start date
        let et = moment(fil.end).format('Y-MM-DD hh:mm:ss'); // end date
        let path = `ls?t=LBT&p=${fil.t}&p=${st}&p=${et}`;
        
        $http.get(getLink(serverIO,pathIO,path)
        ).then(r => {
            if (!r.data) return;

            var colors = [];
            var borders = [];
            var options = {
                scales: {
                    y: {
                        beginAtZero: true
                    }
                }
            };
            var datasets = [
                {
                    label: 'Lượt thích theo thời gian',
                    data: r.data.map(x => x['quantity']),
                    backgroundColor: colors,
                    borderColor: borders,
                    borderRadius: 3,
                    borderWidth: 1
                }
            ];
            
            randomColor(r.data.length, colors, borders)
            chartSet('bar', r.data.map(e => e.about), options, ...datasets)
        }).catch(e => console.error(e))
    }

});

function chartSet(type, labels, options, ...datasets) {
    const chart = document.querySelector('#showChart');
    if(!chart) return;
    while (chart.firstChild) chart.removeChild(chart.lastChild);
    chart.innerHTML = `<canvas class="border border-2"></canvas>`

    new Chart(chart.firstChild, {
        type: type,
        data: {
            labels: labels,
            datasets: datasets
        }, options: options
    });
}
function randomColor(size, colors, borders) {
    if(colors, borders) for (let i = 0; i < size; i++) {
        let color = Math.random() * 360;
        colors.push(`hsl(${color}, 80%, 50%, .25)`);
        borders.push(`hsl(${color}, 80%, 50%, 1)`);
    }
}