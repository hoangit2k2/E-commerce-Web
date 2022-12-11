app.controller('statisticControl', ($scope) => {
    (async (...getTo) => {
        $scope.entity.id = Math.round((Math.random()-1)*1000)
        for (to of getTo) if (typeof (to) == 'string') await $scope.get(to);
        else {
            let keys = Object.keys(to)
            for (k of keys) await $scope.get(to[k], k);
        };
    })('statistic',{users:'users'})
});