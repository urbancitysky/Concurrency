;Project 10
;Author: Shih Kai Chen

(def balance1 (ref 1000))
(def balance2 (ref 2000))
(println "Initial balance1: " @balance1)
(println "Initial balance2: " @balance2)

(def CompletedTransNum (agent 1))
(defn transfer [from to amount futureNum waitingTime] 
	(dosync 
		(println (str "Trying: " futureNum " " waitingTime ))
		(alter from - amount) 
		(Thread/sleep waitingTime)
		(alter to + amount) 
		(do 
			(println (str "Transfer: "futureNum ))
			(send-off CompletedTransNum inc)
		)
	)
)

(def futurePassing_1 
	(future (dotimes [n 10]
			(transfer balance1 balance2 20 1 (rand-int 100)))))

(def futurePassing_2 
	(future (dotimes [n 10]
			(transfer balance1 balance2 15 2 (rand-int 40)))))  

@futurePassing_1
@futurePassing_2
(println "transaction: " @CompletedTransNum)
(println "Balance1: " @balance1)
(println "Balance2: " @balance2)
