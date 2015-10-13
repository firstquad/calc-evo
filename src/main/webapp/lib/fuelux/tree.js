/*
 * Fuel UX Tree
 * https://github.com/ExactTarget/fuelux
 *
 * Copyright (c) 2012 ExactTarget
 * Licensed under the MIT license.
 */

define('fuelux/tree', ['require', 'jquery', 'underscore', 'n2o/tools/helpers'], function (require, $, _, Helpers) {

    var $ = require('jquery');
    var old = $.fn.tree;

    // TREE CONSTRUCTOR AND PROTOTYPE

    var Tree = function (element, options) {
        var tree = this;
        this.$element = $(element);
        this.options = $.extend({}, $.fn.tree.defaults, options);
        this.render();
    };

    Tree.prototype = {
        constructor: Tree,

        render: function () {
            this.populate(this.$element);
        },

        populate: function ($el) {
            var def = $.Deferred();
            var self = this;
            var $parent = $el.parent();
            var loader = $parent.find('.tree-loader:eq(0)');

            loader.show();
            this.options.dataSource.data($el.data(), function (items) {
                loader.hide();

                $.each(items.data, function (index, value) {
                    var $entity;
                    var $val = value;

                    if (value.type === "folder") {
                        $entity = self.$element.find('.tree-folder:eq(0)').clone().show();
                        $entity.find('.tree-folder-name').html(value.name);
                        $entity.find('.n2o-tree-el').attr('title', value.name);
                        $entity.find('.n2o-tree-icon').addClass(value.icon);
                        $entity.find('.tree-loader').html(self.options.loadingHTML).hide();
                        $entity.find('.tree-folder-header').data(value);
                    } else if (value.type === "item") {
                        $entity = self.$element.find('.tree-item-test:eq(0)').clone().show();
                        $entity.find('.tree-item-name').html(value.name);
                        $entity.find('.n2o-tree-el').attr('title', value.name);
                        $entity.find('.n2o-tree-icon').addClass(value.icon);
                        $entity.find('.tree-item').data(value);
                    }

                    // Decorate $entity with data making the element
                    // easily accessable with libraries like jQuery.
                    //
                    // Values are contained within the object returned
                    // for folders and items as dataAttributes:
                    //
                    // {
                    //     name: "An Item",
                    //     type: 'item',
                    //     dataAttributes = {
                    //         'classes': 'required-item red-text',
                    //         'data-parent': parentId,
                    //         'guid': guid
                    //     }
                    // };

                    var dataAttributes = value.dataAttributes || [];
                    $.each(dataAttributes, function (key, value) {
                        switch (key) {
                            case 'class':
                            case 'classes':
                            case 'className':
                                $entity.addClass(value);
                                break;

                            // id, style, data-*
                            default:
                                if ($val.type === "folder") {
                                    $entity.find('.tree-folder-header').attr(key, value);
                                } else if ($val.type === "item") {
                                    $entity.find('.tree-item').attr(key, value);
                                }
                                break;
                        }
                    });

                    if ($el.hasClass('tree-folder-header')) {
                        $parent.find('.tree-folder-content:eq(0)').append($entity);
                    } else {
                        $el.append($entity);
                    }
                });

                // return newly populated folder
                self.$element.trigger('loaded', $parent);
                def.resolve();
            });
            return def;
        },

        // fix: path
        selectItem: function (el) {
            var $el = $(el);
            var $all = this.$element.find('.tree-selected');
            var data = [];

            if (this.options.multiSelect) {
                $.each($all, function (index, value) {
                    var $val = $(value);
                    if ($val[0] !== $el[0]) {
                        data.push($(value).data());
                    }
                });
            } else if ($all[0] !== $el[0]) {
                $all.removeClass('tree-selected')
                    .find('i').removeClass('icon-ok');
                data.push($el.data());
            }

            var eventType = 'selected';
            $el.addClass('tree-selected');
            if (data.length) {
                this.$element.trigger('selected', {info: data, eventType: eventType});
            }

            // Return new list of selected items, the item
            // clicked, and the type of event:
            $el.trigger('updated', {
                info: data,
                item: $el,
                eventType: eventType
            });
        },

        selectFolder: function (el, options) {
            options = options || {};
            var d = $.Deferred();
            var $el = $(el);
            var $parent = $el.parent();
            var $all = this.$element.find('.tree-selected');
            var $treeFolderContent = $parent.find('.tree-folder-content');
            var $treeFolderContentFirstChild = $treeFolderContent.eq(0);
            var toggle = (_.isUndefined(options.toggle)) ? true : options.toggle ;

            if (toggle) {
                var eventType, classToTarget, classToAdd;
                if ($el.prev().is('.n2o-tree-close-flag')) {
                    eventType = 'opened';
                    classToTarget = '.n2o-tree-close-flag';
                    classToAdd = 'n2o-tree-open-flag';

                    $treeFolderContentFirstChild.show();
                    if (!$treeFolderContent.children().length) {
                        this.populate($el).done(function(){
                            d.resolve();
                        });
                    }
                } else {
                    eventType = 'closed';
                    classToTarget = '.n2o-tree-open-flag';
                    classToAdd = 'n2o-tree-close-flag';

                    $treeFolderContentFirstChild.hide();
                    if (!this.options.cacheItems) {
                        $treeFolderContentFirstChild.empty();
                    }
                }

                $parent.find(classToTarget).eq(0)
                    .removeClass('n2o-tree-close-flag n2o-tree-open-flag')
                    .addClass(classToAdd);

                this.$element.trigger(eventType, $el.data());
            }

            //fix
            if (!options.noSelect) {
                $all.removeClass('tree-selected');
                $el.addClass("tree-selected");
            }

            return d;
        },

        openFolder: function (el) {
            var $el = $(el);
            var $parent = $el.parent();
            var $all = this.$element.find('.tree-selected');
            var $treeFolderContent = $parent.find('.tree-folder-content');
            var $treeFolderContentFirstChild = $treeFolderContent.eq(0);

            var eventType, classToTarget, classToAdd;

            eventType = 'opened';
            classToTarget = '.n2o-tree-close-flag';
            classToAdd = 'n2o-tree-open-flag';

            $treeFolderContentFirstChild.show();
            if (!$treeFolderContent.children().length) {
                this.populate($el);
            }

            $parent.find(classToTarget).eq(0)
                .removeClass('n2o-tree-close-flag n2o-tree-open-flag')
                .addClass(classToAdd);

            this.$element.trigger(eventType, $el.data());
        },

        closeFolder: function (el) {
            var $el = $(el);
            var $parent = $el.parent();
            var $all = this.$element.find('.tree-selected');
            var $treeFolderContent = $parent.find('.tree-folder-content');
            var $treeFolderContentFirstChild = $treeFolderContent.eq(0);

            var eventType, classToTarget, classToAdd;

            eventType = 'closed';
            classToTarget = '.n2o-tree-open-flag';
            classToAdd = 'n2o-tree-close-flag';

            $treeFolderContentFirstChild.hide();
            if (!this.options.cacheItems) {
                $treeFolderContentFirstChild.empty();
            }

            $parent.find(classToTarget).eq(0)
                .removeClass('n2o-tree-close-flag n2o-tree-open-flag')
                .addClass(classToAdd);

            this.$element.trigger(eventType, $el.data());
        },

        selectedItems: function () {
            var $sel = this.$element.find('.tree-selected');
            var data = [];

            $.each($sel, function (index, value) {
                data.push($(value).data());
            });
            return data;
        },

        // collapses open folders
        collapse: function () {
            var cacheItems = this.options.cacheItems;

            // find open folders
            this.$element.find('.n2o-tree-open-flag').each(function () {
                // update icon class
                var $this = $(this)
                    .removeClass('n2o-tree-close-flag n2o-tree-open-flag')
                    .addClass('n2o-tree-close-flag');

                // "close" or empty folder contents
                var $parent = $this.parent();
                var $folder = $parent.children('.tree-folder-content');

                $folder.hide();
                if (!cacheItems) {
                    $folder.empty();
                }
            });
        }
    };


    // TREE PLUGIN DEFINITION

    $.fn.tree = function (option) {
        var args = Array.prototype.slice.call(arguments, 1);
        var methodReturn;

        var $set = this.each(function () {
            var $this = $(this);
            var data = $this.data('tree');
            var options = typeof option === 'object' && option;

            if (!data) $this.data('tree', (data = new Tree(this, options) ));
            if (typeof option === 'string') methodReturn = data[ option ].apply(data, args);
        });

        return ( methodReturn === undefined ) ? $set : methodReturn;
    };

    $.fn.tree.defaults = {
        multiSelect: false,
        loadingHTML: '<div>Loading...</div>',
        cacheItems: true
    };

    $.fn.tree.Constructor = Tree;

    $.fn.tree.noConflict = function () {
        $.fn.tree = old;
        return this;
    };
});