format long
[runnumber, forumid, startPercent, endPercent, mode, step, distance, USERVIEWGEOMETRICVALUEP, newThreadProb, filterShowAll, filterShowWithNoReply, filterShowHasReply] = textread('evaluation_result.csv','%s %s %s %s %s %s %s %s %s %s %s %s', 'headerlines', 7,'delimiter', ',');
filtermap = struct();
for i = 1:numel(forumid)
 id = strrep(forumid(i), '"', '');
 diststring = strrep(distance(i), '"', '');
 dist = cellfun(@str2double,diststring);

 if (isfield(filtermap, id{1})==0)
  filtermap.(id{1}) = dist;
 else
  filtermap.(id{1})(end+1) = dist;
 endif
end

names = fieldnames(filtermap)(end:-1:1);
filtermeans = [];
for i = 1:length(names)
 filtermeans(end+1) = mean(getfield(filtermap, names(i){1}));
end


[runnumber, forumid, mode, step, distance, newThreadProb, powerValue] = textread('evaluation_pa_powerValue_0_result.csv','%s %s %s %s %s %s %s', 'headerlines', 7,'delimiter', ',');
randommap = struct();
for i = 1:numel(forumid)
 id = strrep(forumid(i), '"', '');
 diststring = strrep(distance(i), '"', '');
 dist = cellfun(@str2double,diststring);

 if (isfield(randommap, id{1})==0)
  randommap.(id{1}) = dist;
 else
  randommap.(id{1})(end+1) = dist;
 endif
end

randommeans = [];
for i = 1:length(names)
 randommeans(end+1) = mean(getfield(randommap, names(i){1}));
end


[runnumber, forumid, mode, step, distance, newThreadProb, powerValue] = textread('evaluation_pa_powerValue_1_result.csv','%s %s %s %s %s %s %s', 'headerlines', 7,'delimiter', ',');
pamap = struct();
for i = 1:numel(forumid)
 id = strrep(forumid(i), '"', '');
 diststring = strrep(distance(i), '"', '');
 dist = cellfun(@str2double,diststring);

 if (isfield(pamap, id{1})==0)
  pamap.(id{1}) = dist;
 else
  pamap.(id{1})(end+1) = dist;
 endif
end

pameans = [];
for i = 1:length(names)
 pameans(end+1) = mean(getfield(pamap, names(i){1}));
end

semilogy(filtermeans, "marker", 'x', "color", 'r'); hold on;
semilogy(randommeans, "marker", 'x', "color", 'g'); hold on;
semilogy(pameans, "marker", 'x', "color", 'b');
set(gca(),'xtick',1:length(names));
set(gca(),'xticklabel',names);

