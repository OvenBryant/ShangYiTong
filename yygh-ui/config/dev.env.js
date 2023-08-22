'use strict'
const merge = require('webpack-merge')
const prodEnv = require('./prod.env')

module.exports = merge(prodEnv, {
  NODE_ENV: '"development"',
  // BASE_API: '"http://localhost:9001"',   // 必须要加引号 使用的是nginx请求转发
  BASE_API: '"http://localhost"',   // 必须要加引号 使用的是nginx请求转发

})
