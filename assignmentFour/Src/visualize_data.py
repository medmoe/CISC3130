import matplotlib.pyplot as plt 
import numpy as np
with open('../Data/output/years.txt') as file_object:
	
	#create two lists one holds the years, the other holds the number of movies.
	years = []
	number_of_movies = []
	#for each line, we parse the year and the number of the movies and we add them to lists.
	for line in file_object:
		a = line.split(':')
		try: # neglect the movies that has unknown release year.
			years.append(int(a[0]))
			number_of_movies.append(int(a[1]))
		except ValueError:
			pass
		
	# customize the size of the figure
	fig = plt.figure(figsize=(20,8))

	#set chart title and label axes
	plt.title("Movies Released Each Year", fontsize=24)
	plt.ylabel("number of movies", fontsize=20)

	#customize tick labels
	plt.xticks(years, rotation='vertical')
	plt.xticks(np.arange(1900, 2030, step=2))
	plt.tick_params(axis='both', labelsize=14)

	#plot the years and number of movies
	plt.plot(years, number_of_movies)

	#save the figure
	plt.savefig('../Data/output/movies_released_each_year.png')
