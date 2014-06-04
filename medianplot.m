format long


forum = 144;
forumString = num2str(forum);

% read evaluation results
 evalFile = ls('evaluation*result.csv');
 % results for ls vary with used os
 if isunix
    evalFile = evalFile(1:end-1);
 end
 
if strcmp(evalFile,'evaluation_result.csv') | strcmp(evalFile,'evaluation_userview_result.csv')
    model='filter';
    [runnumber, forumid, startPercent, endPercent, mode, step, distance, USERVIEWGEOMETRICVALUEP, newThreadProb, filterShowAll, filterShowWithNoReply, filterShowHasReply] = textread(evalFile,'%s %s %s %s %s %s %s %s %s %s %s %s', 'headerlines', 7,'delimiter', ',');
else
    model='pa';
    [runnumber, forumid, startPercent, endPercent, mode, step, distance, newThreadProb, powerValue] = textread(evalFile,'%s %s %s %s %s %s %s %s %s', 'headerlines', 7,'delimiter', ',');
end

dist = cellfun(@str2double,strrep(distance, '"', ''));

forumdistances = sort(dist(strcmp(forumid,strcat('"',forumString,'"'))));
med = forumdistances(ceil(end/2));
medianRun = runnumber(strcmp(forumid,strcat('"',forumString,'"')) & med == dist)

runnumberString = strrep(medianRun{1}, '"', '')
relPath = './simulation_results/';

distrfile = ls(strcat(relPath,'output_',forumString,'(',runnumberString,').csv'));
% results for ls vary with used os
if ispc 
   distrfile = strcat(relPath, distrfile);
else
   distrfile = distrfile(1:end-1);
end

if strcmp(model,'pa')
    [threadsize,frequency,runcount, pvalue] = textread(distrfile,'%f %f %f %f', 'headerlines', 1,'delimiter', ',');
else
    [threadsize,frequency,runcount, userviewparam ,filterShowAllParam,filterShowWithNoReplyParam,filterShowHasReplyParam,policyLatestActivityCreationDate,policyThreadCreationDate,policyThreadSize] = textread(distrfile,'%f %f %f %f %f %f %f %f %f %f', 'headerlines', 1,'delimiter', ',');
end

bar(threadsize,frequency)

