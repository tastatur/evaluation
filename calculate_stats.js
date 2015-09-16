load('stats.js')

db.ratings.mapReduce(maphelpQuality, reduce, {finalize:finalize, out:{replace: "helpQuality"}})
db.ratings.mapReduce(mapqualityRating_misc, reduce, {finalize:finalize, out:{replace: "qualityRating_misc"}})
db.ratings.mapReduce(mapqualityRating_newspapers, reduce, {finalize:finalize, out:{replace: "qualityRating_newspapers"}})
db.ratings.mapReduce(mapspeedRating, reduce, {finalize:finalize, out:{replace: "speedRating"}})
