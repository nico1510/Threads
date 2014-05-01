format long

# read evaluation results of filter model
[runnumber, forumid, startPercent, endPercent, mode, step, distance, USERVIEWGEOMETRICVALUEP, newThreadProb, filterShowAll, filterShowWithNoReply, filterShowHasReply] = textread('evaluation_result.csv','%s %s %s %s %s %s %s %s %s %s %s %s', 'headerlines', 7,'delimiter', ',');
filtermap = struct();
for i = 1:numel(forumid)
 id = strrep(forumid(i), '"', '');
 diststring = strrep(distance(i), '"', '');
 dist = cellfun(@str2double,diststring);
 filtermap.(id{1})(end+1) = dist;
end

names = fieldnames(filtermap)(end:-1:1);
filtermeans = [];
for i = 1:length(names)
 filtermeans(end+1) = mean(getfield(filtermap, names{i}));
end

# read evaluation results of random model
[runnumber, forumid, mode, step, distance, newThreadProb, powerValue] = textread('evaluation_pa_powerValue_0_result.csv','%s %s %s %s %s %s %s', 'headerlines', 7,'delimiter', ',');
randommap = struct();
for i = 1:numel(forumid)
 id = strrep(forumid(i), '"', '');
 diststring = strrep(distance(i), '"', '');
 dist = cellfun(@str2double,diststring);
 randommap.(id{1})(end+1) = dist;
end

randommeans = [];
for i = 1:length(names)
 randommeans(end+1) = mean(getfield(randommap, names{i}));
end

# read evaluation results of pa model
[runnumber, forumid, mode, step, distance, newThreadProb, powerValue] = textread('evaluation_pa_powerValue_1_result.csv','%s %s %s %s %s %s %s', 'headerlines', 7,'delimiter', ',');
pamap = struct();
for i = 1:numel(forumid)
 id = strrep(forumid(i), '"', '');
 diststring = strrep(distance(i), '"', '');
 dist = cellfun(@str2double,diststring);
 pamap.(id{1})(end+1) = dist;
end

pameans = [];
for i = 1:length(names)
 pameans(end+1) = mean(getfield(pamap, names{i}));
end


# compute thresholds
thresholds = [];
for i = 1:length(names)
 distrfile = glob (["./sample_distributions/sample-distribution_",names{i},"*.csv"]){1};
 [threadlength, frequency] = textread(distrfile,'%d %d', 'delimiter', ',');
 thresholds(end+1) = 1.92/sqrt(sum(frequency));
end

# plot means and threshold
semilogy(filtermeans, "marker", 'x', "color", 'r'); hold on;
semilogy(randommeans, "marker", 'x', "color", 'g'); hold on;
semilogy(pameans, "marker", 'x', "color", 'b'); hold on;
semilogy(thresholds, "linestyle", "--", "color", "black");
legend ("filter","random","pa","threshold");
set(gca(),'xtick',1:length(names));
set(gca(),'xticklabel',names);

