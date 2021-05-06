#!/bin/bash

# This may require that you have already committed your current state

# Add the original "forked" repository with the name "upstream"
git remote add upstream git@gitlab.liu.se:jonkv82/javaoo-base.git

# Check out the current master branch
# TODO: Changes from master to main in release 14.0, shipping on June 22, 2021
git checkout master

# Fetch any new commits from the master branch of the "upstream" repository
git fetch upstream

# Merge those commits into the current master branch.
# This should result in you having to write a merge commit message.
git pull upstream master
