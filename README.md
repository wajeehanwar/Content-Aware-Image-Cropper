<h1 align="center">
  <br>
  <a href="https://raw.githubusercontent.com/wajeehanwar/Content-Aware-Image-Cropper"><img src="https://raw.githubusercontent.com/wajeehanwar/Content-Aware-Image-Cropper/master/images/mandrill_vseam.jpg" alt="CAIC" width="400"></a>
  <br>
  Content-Aware Image Cropper
  <br>
</h1>

<h4 align="center">A content-aware image cropper that preserves the most interesting features of an image.</h4>

<p align="center">
<a href="#overview">Overview</a> •
<a href="#view">View</a> •
  <a href="#how-to-use">How To Use</a> •
  <a href="#download">Download</a> •
  <a href="#credits">Credits</a> •
  <a href="#license">License</a>
</p>

## Overview

Seam carving is a content-aware image resizing technique where the image is reduced in size by one pixel of height (or width) at a time. Although the underlying algorithm is simple and elegant, it was not discovered until 2007 by Shai Avidan and Ariel Shamir. Now, it is a core feature in many computer graphics applications.

A vertical seam in an image is a path of pixels connected from the top to the bottom with one pixel in each row, while a horizontal seam is a path of pixels connected from the left to the right with one pixel in each column. SeamCarving is a created data type that resizes a W\*H image using this seam-carving technique.

Below left is the original 298\*298 pixel image; below right is the result after removing 50 vertical and horizontal seams. Unlike standard content-agnostic resizing techniques (such as cropping and scaling), seam carving preserves the most interesting features (aspect ratio, set of objects present, etc.) of the image.

## View

<div><h4>Original</h4>
<a href="https://raw.githubusercontent.com/wajeehanwar/Content-Aware-Image-Cropper"><img src="https://raw.githubusercontent.com/wajeehanwar/Content-Aware-Image-Cropper/master/images/chameleon.png" alt="Original Image" width="550"></a>

<h4>Content-Aware Crop</h4><a href="https://raw.githubusercontent.com/wajeehanwar/Content-Aware-Image-Cropper"><img src="https://raw.githubusercontent.com/wajeehanwar/Content-Aware-Image-Cropper/master/images/chameleon_resized.png" alt="Content-Aware Cropped Image" width="500"></a>
</span>
</div>

## How To Use

Requires installation of [Java](https://java.com/en/download/help/download_options.xml). To compile and run this application from your command line:

```bash
# To compile program.

$ javac SeamCarver.java
```

```bash
# To run program.
# java SeamCarver [filename] [numPixels to remove]
# [y (if horizontal carving) | N (otherwise)]

$ java SeamCarver image.jpg 100 y
```

## Download

You can [download](https://github.com//wajeehanwar/Content-Aware-Image-Cropper) here.

## Credits

- Swami Iyer
- Robert Sedgewick
- Shai Avidan
- Ariel Shamir

This software was developed using the following:

- Java

## License

MIT

---

> GitHub [@wajeehanwar](https://github.com/wajeehanwar)
