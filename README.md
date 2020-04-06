<br />
<p align="center">

  <h3 align="center">Kohonen Self Organizing Map</h3>

  <p align="center">
    A Kohonen SOM based clustering application.
    <br />
    <br />
    <a href="https://github.com/izzettunc/Kohonen-SOM/issues">Report Bug</a>
    ·
    <a href="https://github.com/izzettunc/Kohonen-SOM/issues">Request Feature</a>
  </p>
</p>



<!-- TABLE OF CONTENTS -->
## Table of Contents

* [About the Project](#about-the-project)
* [Getting Started](#getting-started)
  * [Installation](#installation)
* [Usage](#usage)
* [Roadmap](#roadmap)
* [License](#license)
* [Contact](#contact)



<!-- ABOUT THE PROJECT -->
## About The Project

![Product Name Screen Shot][product-screenshot]

This project made as a class assignment.It's purpose basically parsing the given csv data , preprocessed it and then training the som for clustering it.

**Features:**

* You can cluster the given csv data.
* You can visualize the result by U-Matrix or Hit-Map that represented on heat map.
* You can print each points best matching unit and weights of neurons in a csv file.
* There is 2 different topological neighbourhood function (Gauss and Mexican Hat)

### What is Self Organizing Map ?

A self-organizing map (SOM) or self-organizing feature map (SOFM) is a type of artificial neural network (ANN) that is trained using unsupervised learning to produce a low-dimensional (typically two-dimensional), discretized representation of the input space of the training samples, called a map, and is therefore a method to do dimensionality reduction. Self-organizing maps differ from other artificial neural networks as they apply competitive(or cooperative) learning as opposed to error-correction learning (such as backpropagation with gradient descent), and in the sense that they use a neighborhood function to preserve the topological properties of the input space.

This makes SOMs useful for visualization by creating low-dimensional views of high-dimensional data, akin to multidimensional scaling. The artificial neural network introduced by the Finnish professor Teuvo Kohonen in the 1980s is sometimes called a Kohonen map or network. The Kohonen net is a computationally convenient abstraction building on biological models of neural systems from the 1970s and morphogenesis models dating back to Alan Turing in the 1950s.

<!-- GETTING STARTED -->
## Getting Started

To get a local copy up and running follow these simple steps.

### Installation

1.  Clone the repo
```sh
git clone https://github.com/izzettunc/Kohonen-SOM.git 
```
2. Open src folder in a Java Editor

3. Make changes, run it, use it whatever you like :smile:

#### Important Notice

I used [JHeatChart](http://www.javaheatmap.com) for visualization of hit values. It's basic and easy to use tool.Go check it out :smile:

<!-- USAGE EXAMPLES -->
## Usage

Well, there is no UI for now so you have to change the setting in code.But when I add the UI I will update here.

For now there is some screenshots that shows what can you do with this.

* You have to give a data type array for preprocessing the data shown with red arrow.( I think there is enough data types for som algorithm such as double,int,ordinal,categorical and bool )
* If you want to change starting radius shown in blue for decaying neighbourhood func. basically change it to positive value.Any negative value is going to set it to original value which is usually recommend to be (width plus height of the map ) divided by 2
* And you can change the visualization a bit by changing the color scale shown in green
![settings][settings-screenshot]

And these are example results

![result][res-screenshot]

![result2][res2-screenshot]

<!-- ROADMAP -->
## Roadmap

See the [open issues](https://github.com/izzettunc/newsClassification/issues) for a list of proposed features (and known issues).

<!-- LICENSE -->
## License

Distributed under the MIT License. See `LICENSE` for more information.

<!-- CONTACT -->
## Contact

İzzet Tunç - izzet.tunc1997@gmail.com
<br>
[![LinkedIn][linkedin-shield]][linkedin-url]

[linkedin-shield]: https://img.shields.io/badge/-LinkedIn-black.svg?style=flat-square&logo=linkedin&colorB=555
[linkedin-url]: https://www.linkedin.com/in/izzettunc
[product-screenshot]: data/screenshots/landing.png

[settings-screenshot]: data/screenshots/settings.png
[res-screenshot]: data/screenshots/res.png
[res2-screenshot]: data/screenshots/res2.png
