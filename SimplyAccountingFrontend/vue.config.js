module.exports = {
    transpileDependencies: ['vuetify'],
    configureWebpack: {
        devtool: 'source-map',
    },
    css: {
        loaderOptions: {
            scss: {
                additionalData: `@import "~@/scss/_globalStyle.scss";`
            }
        }
    }
}
