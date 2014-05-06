annealing:
	make clean
	../netlogo-headless.sh --model ./Threads/SimAnn.nlogo --experiment annealing --table ./Threads/annealing_result.csv --threads 5

eval:
	make clean
	../netlogo-headless.sh --model ./Threads/Eval.nlogo --experiment eval --table ./Threads/evaluation_result.csv --threads 5

pa_annealing:
	make clean
	../netlogo-headless.sh --model ./Threads/SimAnn.nlogo --experiment annealing-pa --table ./Threads/annealing_pa_result.csv --threads 5

pa_eval:
	make clean
	../netlogo-headless.sh --model ./Threads/Eval.nlogo --experiment eval-pa --table ./Threads/evaluation_pa_result.csv --threads 5

userview_annealing:
	make clean
	../netlogo-headless.sh --model ./Threads/SimAnn.nlogo --experiment annealing-userview --table ./Threads/annealing_result.csv --threads 5

userview_eval:
	make clean
	../netlogo-headless.sh --model ./Threads/Eval.nlogo --experiment eval-userview --table ./Threads/evaluation_result.csv --threads 5

all_filter:
	make annealing
	make eval

all_pa:
	make pa_annealing
	make pa_eval

all_userview:
	make userview_annealing
	make userview_eval

pa_fixed-0:
	make clean
	../netlogo-headless.sh --model ./Threads/Eval_pa_fixed-values.nlogo --experiment pa-fixed-0 --table ./Threads/evaluation_pa_powerValue_0_result.csv

pa_fixed-1:
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
	@rm -rf ./evaluation_pa_result.csv
	@rm -rf ./evaluation_pa_powerValue_0_result.csv
	@rm -rf ./evaluation_pa_powerValue_1_result.csv
