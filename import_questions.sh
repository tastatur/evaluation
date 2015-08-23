#!/bin/bash

mongoimport -c available_search_queries --file data/available_search_queries.json --db evaluation 
