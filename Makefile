annealing:
	../netlogo-headless.sh --model ./Threads/SimAnn.nlogo --experiment annealing --table ./Threads/annealing_result.csv

eval:
	../netlogo-headless.sh --model ./Threads/Eval.nlogo --experiment eval --table ./Threads/evaluation_result.csv

clean:
	find simulation_results -name "*.csv" -delete
	find annealing_results -name "*.csv" -delete
	find sample_distributions -name "*.csv" -delete

new:
	make clean
	rm ./annealing_result.csv
	rm ./evaluation_results.csv
