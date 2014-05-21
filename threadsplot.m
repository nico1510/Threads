format long

% read evaluation results of filter model
[runnumber, forumid, startPercent, endPercent, mode, step, distance, USERVIEWGEOMETRICVALUEP, newThreadProb, filterShowAll, filterShowWithNoReply, filterShowHasReply] = textread('evaluation_result.csv','%s %s %s %s %s %s %s %s %s %s %s %s', 'headerlines', 7,'delimiter', ',');
filtermap = struct();
for i = 1:numel(forumid)
 id = char(strcat('forum', strrep(forumid(i), '"', '')));
 diststring = strrep(distance(i), '"', '');
 dist = cellfun(@str2double,diststring);
 if isfield(filtermap, id) == 0
     filtermap.(id) = [];
 else
     filtermap.(id)(end+1) = dist;
 end
end

tmp_names = fieldnames(filtermap);
names = tmp_names(end:-1:1);

filtermeans = [];
for i = 1:length(names)
 filtermeans(end+1) = mean(getfield(filtermap, names{i}));
end

% read evaluation results of uverview model
[runnumber, forumid, startPercent, endPercent, mode, step, distance, USERVIEWGEOMETRICVALUEP, newThreadProb, filterShowAll, filterShowWithNoReply, filterShowHasReply] = textread('evaluation_userview_result.csv','%s %s %s %s %s %s %s %s %s %s %s %s', 'headerlines', 7,'delimiter', ',');
userviewmap = struct();
for i = 1:numel(forumid)
 id = char(strcat('forum', strrep(forumid(i), '"', '')));
 diststring = strrep(distance(i), '"', '');
 dist = cellfun(@str2double,diststring);
 if isfield(userviewmap, id) == 0
     userviewmap.(id) = [];
 else
     userviewmap.(id)(end+1) = dist;
 end
end

userviewmeans = [];
for i = 1:length(names)
 userviewmeans(end+1) = mean(getfield(userviewmap, names{i}));
end

% read evaluation results of random model
[runnumber, forumid, mode, step, distance, newThreadProb, powerValue] = textread('./evaluation_pa_powerValue_0_result.csv','%s %s %s %s %s %s %s', 'headerlines', 7,'delimiter', ',');
randommap = struct();
for i = 1:numel(forumid)
 id = char(strcat('forum', strrep(forumid(i), '"', '')));
 diststring = strrep(distance(i), '"', '');
 dist = cellfun(@str2double,diststring);
 if isfield(randommap, id) == 0
     randommap.(id) = [];
 else
     randommap.(id)(end+1) = dist;
 end
end

randommeans = [];
for i = 1:length(names)
 randommeans(end+1) = mean(getfield(randommap, names{i}));
end

% read evaluation results of pa model
[runnumber, forumid, mode, step, distance, newThreadProb, powerValue] = textread('./evaluation_pa_powerValue_1_result.csv','%s %s %s %s %s %s %s', 'headerlines', 7,'delimiter', ',');
pamap = struct();
for i = 1:numel(forumid)
 id = char(strcat('forum', strrep(forumid(i), '"', '')));
 diststring = strrep(distance(i), '"', '');
 dist = cellfun(@str2double,diststring);
 if isfield(pamap, id) == 0
     pamap.(id) = [];
 else
     pamap.(id)(end+1) = dist;
 end
end

pameans = [];
for i = 1:length(names)
 pameans(end+1) = mean(getfield(pamap, names{i}));
end


% compute thresholds
thresholds = [];
relPath = './sample_distributions/';
for i = 1:length(names)
 distrfile = ls(strcat(relPath,'sample-distribution_',names{i}(6:end),'*.csv'));
 % results for ls vary with used os
 if ispc 
    distrfile = strcat(relPath, distrfile);
 else
    distrfile = distrfile(1:end-1);
 end
 tempDistrFile = strcat(relPath, distrfile(1:end-1));
 [threadlength, frequency] = textread(distrfile,'%d %d', 'delimiter', ',');
 thresholds(end+1) = 1.92/sqrt(sum(frequency));
end

% plot means and threshold
semilogy(filtermeans, 'marker', 'x', 'color', 'r'); hold on;
semilogy(randommeans, 'marker', 'x', 'color', 'g'); hold on;
semilogy(pameans, 'marker', 'x', 'color', 'b'); hold on;
semilogy(userviewmeans, 'marker', 'x', 'color', 'm'); hold on;
semilogy(thresholds, 'linestyle', '--', 'color', 'black');
legend ('filter','random','pa','userview','threshold');
set(gca(),'xtick',1:length(names));
set(gca(),'xticklabel',cellfun(@(x) strrep(x,'forum',''),names,'Un',0));


% labels = cellfun(@str2double,names);
% 
% R = [labels, filtermeans', randommeans', pameans', userviewmeans', thresholds'];
% csvwrite('data.csv', R, '-append');

