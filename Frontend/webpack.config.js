const path = require('path');
const HtmlWebpackPlugin = require('html-webpack-plugin');
const CopyPlugin = require("copy-webpack-plugin");
const { CleanWebpackPlugin } = require('clean-webpack-plugin');

module.exports = {
  optimization: {
    usedExports: true
  },
  entry: {
    homePage: path.resolve(__dirname, 'src', 'pages', 'homePage.js'),
    recipePage: path.resolve(__dirname, 'src', 'pages', 'recipePage.js'),
    userPage: path.resolve(__dirname, 'src', 'pages', 'userPage.js'),
    aboutPage: path.resolve(__dirname, 'src', 'pages', 'aboutPage.js'),
    recipeBuilder: path.resolve(__dirname, 'src', 'pages', 'recipeBuilder.js'),
  },

  output: {
    path: path.resolve(__dirname, 'dist'),
    filename: '[name].js',
  },
  devServer: {
    https: false,
    port: 8080,
    open: true,
    proxy: [
      {
        context: [
          '/',
        ],
        target: 'http://localhost:5001'
      }
    ]
  },
  plugins: [
    new HtmlWebpackPlugin({
      template: './src/index.html',
      filename: 'index.html',
      inject: false
    }),
    new HtmlWebpackPlugin({
      template: './src/recipePage.html',
      filename: 'recipePage.html',
      inject: false
    }),
    new HtmlWebpackPlugin({
      template: './src/about.html',
      filename: 'about.html',
      inject: false
    }),
    new HtmlWebpackPlugin({
      template: './src/recipeBuilder.html',
      filename: 'recipeBuilder.html',
      inject: false
    }),
    new HtmlWebpackPlugin({
        template: './src/userPage.html',
        filename: 'userPage.html',
        inject: false
    }),
    new CopyPlugin({
      patterns: [
        {
          from: path.resolve('src/css'),
          to: path.resolve("dist/css")
        }
      ]
    }),
    new CopyPlugin({
          patterns: [
            {
              from: path.resolve('src/images'),
              to: path.resolve("dist/images")
            }
          ]
        }),
    new CleanWebpackPlugin()
  ]
}
