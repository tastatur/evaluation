function maphelpQuality() {
    emit(this.engine, // Or put a GROUP BY key here
         {sum: this.helpQuality, // the field you want stats for
          min: this.helpQuality,
          max: this.helpQuality,
          count:1,
          diff: 0, // M2,n:  sum((val-mean)^2)
    });
}


function mapqualityRating_misc() {
    emit(this.engine, // Or put a GROUP BY key here
         {sum: this.qualityRating_misc, // the field you want stats for
          min: this.qualityRating_misc,
          max: this.qualityRating_misc,
          count:1,
          diff: 0, // M2,n:  sum((val-mean)^2)
    });
}

function mapqualityRating_newspapers() {
    emit(this.engine, // Or put a GROUP BY key here
         {sum: this.qualityRating_newspapers, // the field you want stats for
          min: this.qualityRating_newspapers,
          max: this.qualityRating_newspapers,
          count:1,
          diff: 0, // M2,n:  sum((val-mean)^2)
    });
}

function mapspeedRating() {
    emit(this.engine, // Or put a GROUP BY key here
         {sum: this.speedRating, // the field you want stats for
          min: this.speedRating,
          max: this.speedRating,
          count:1,
          diff: 0, // M2,n:  sum((val-mean)^2)
    });
}

function reduce(key, values) {
    var a = values[0]; // will reduce into here
    for (var i=1/*!*/; i < values.length; i++){
        var b = values[i]; // will merge 'b' into 'a'


        // temp helpers
        var delta = a.sum/a.count - b.sum/b.count; // a.mean - b.mean
        var weight = (a.count * b.count)/(a.count + b.count);
        
        // do the reducing
        a.diff += b.diff + delta*delta*weight;
        a.sum += b.sum;
        a.count += b.count;
        a.min = Math.min(a.min, b.min);
        a.max = Math.max(a.max, b.max);
    }

    return a;
}

function finalize(key, value){ 
    value.avg = value.sum / value.count;
    value.variance = value.diff / value.count;
    value.stddev = Math.sqrt(value.variance);
    return value;
}

