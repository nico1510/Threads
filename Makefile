annealing_filter:
	make clean
	../netlogo-headless.sh --model ./Threads/SimAnn.nlogo --experiment annealing --table ./Threads/annealing_result.csv --threads 5

eval_filter:
	make clean
	../netlogo-headless.sh --model ./Threads/Eval.nlogo --experiment eval --table ./Threads/evaluation_result.csv --threads 5

annealing_pa:
	make clean
	../netlogo-headless.sh --model ./Threads/SimAnn.nlogo --experiment annealing-pa --table ./Threads/annealing_pa_result.csv --threads 5

eval_pa:
	make clean
	../netlogo-headless.sh --model ./Threads/Eval.nlogo --experiment eval-pa --table ./Threads/evaluation_pa_result.csv --threads 5

annealing_userview:
	make clean
	../netlogo-headless.sh --model ./Threads/SimAnn.nlogo --experiment annealing-userview --table ./Threads/annealing_userview_result.csv --threads 5

eval_userview:
	make clean
	../netlogo-headless.sh --model ./Threads/Eval.nlogo --experiment eval-userview --table ./Threads/evaluation_userview_result.csv --threads 5

all_filter:
	make annealing_filter
	make eval_filter

all_pa:
	make annealing_pa
	make eval_pa

all_userview:
	make annealing_userview
	make eval_userview

eval_pa_fixed-0:
	make clean
	../netlogo-headless.sh --model ./Threads/Eval_pa_fixed-values.nlogo --experiment pa-fixed-0 --table ./Threads/evaluation_pa_powerValue_0_result.csv

eval_pa_fixed-1:
	make clean
	../netlogo-headless.sh --model ./Threads/Eval_pa_fixed-values.nlogo --experiment pa-fixed-1 --table ./Threads/evaluation_pa_powerValue_1_result.csv


clean:
	find simulation_results -name "*.csv" -delete
	find annealing_results -name "*.csv" -delete
	find sample_distributions -name "*.csv" -delete


new:
	make clean
	@rm -rf ./annealing_result.csv
	@rm -rf ./evaluation_result.csv
	@rm -rf ./annealing_pa_result.csv
	@rm -rf ./annealing_userview_result.csv
	@rm -rf ./evaluation_pa_result.csv
	@rm -rf ./evaluation_userview_result.csv
	@rm -rf ./evaluation_pa_powerValue_0_result.csv
	@rm -rf ./evaluation_pa_powerValue_1_result.csv
