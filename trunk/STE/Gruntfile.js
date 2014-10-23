module.exports = function(grunt) {
  grunt.initConfig({
    pkg: grunt.file.readJSON('package.json'),

    less: {
        development: {
            options: {
                paths: ["war/less"]
            },
            files: [{"war/css/styles.css": "war/less/styles.less"}]
        }
    },
    cssmin: {
      add_banner: {
        options: {
          banner: '/* BBVA Global Customer Solutions minified css file */'
        },
        files: {
          'war/css/styles.min.css': ['war/css/styles.css']
        }
      }
    },
    concat: {
      options: {
        separator: ';',
      },
      dist: {
        src: ['war/js/*.js'],
        dest: 'war/js/dev/scripts.js',
      },
    },
    uglify: {
      options: {
        banner: '/*! <%= pkg.name %> - v<%= pkg.version %> - ' +
        '<%= grunt.template.today("yyyy-mm-dd") %> */',
        mangle: false
      },
      my_target: {
        files: {
          'war/js/prod/scripts.min.js': ['war/js/dev/scripts.js']
        }
      }
    },
    watch: {
      less: {
        files: 'war/less/**/*.less',
        tasks: ['less', 'cssmin']
      },
      concat: {
        files: 'war/js/*.js',
        tasks: ['concat','uglify']
      },
      livereload: {
        // Here we watch the files the less task will compile to
        // These files are sent to the live reload server after less compiles to them
        options: { livereload: true },
        files: ['war/less/**/*.less', 'war/jsp/**/*.jsp', 'war/js/**/*.js', 'war/html/**/*.html'],
      }
    }
  });

  grunt.loadNpmTasks('grunt-contrib-less');
  grunt.loadNpmTasks('grunt-contrib-cssmin');
  grunt.loadNpmTasks('grunt-contrib-concat');
  grunt.loadNpmTasks('grunt-contrib-uglify');
  grunt.loadNpmTasks('grunt-contrib-watch');

  grunt.registerTask('default', ['less','cssmin','uglify','watch']);
}