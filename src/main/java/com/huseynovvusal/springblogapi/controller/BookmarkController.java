package com.huseynovvusal.springblogapi.controller;

import com.huseynovvusal.springblogapi.service.BookmarkService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * Controller for managing blog bookmarks.
 * Supports adding, removing, checking, toggling, and listing bookmarks.
 */
@RestController
@RequestMapping("/api/bookmarks")
@RequiredArgsConstructor
public class BookmarkController {

    private static final Logger logger = LoggerFactory.getLogger(BookmarkController.class);
    private final BookmarkService bookmarkService;

    /**
     * Adds a bookmark for the specified blog.
     *
     * @param blogId the ID of the blog to bookmark
     * @return HTTP 204 No Content on success
     */
    @PostMapping("/{blogId}")
    public ResponseEntity<Void> add(@PathVariable Long blogId) {
        logger.info("Adding bookmark for blog ID: {}", blogId);
        bookmarkService.addBookmark(blogId);
        return ResponseEntity.noContent().build();
    }

    /**
     * Removes a bookmark for the specified blog.
     *
     * @param blogId the ID of the blog to unbookmark
     * @return HTTP 204 No Content on success
     */
    @DeleteMapping("/{blogId}")
    public ResponseEntity<Void> remove(@PathVariable Long blogId) {
        logger.info("Removing bookmark for blog ID: {}", blogId);
        bookmarkService.removeBookmark(blogId);
        return ResponseEntity.noContent().build();
    }

    /**
     * Checks if the specified blog is bookmarked by the current user.
     *
     * @param blogId the ID of the blog to check
     * @return true if bookmarked, false otherwise
     */
    @GetMapping("/check")
    public ResponseEntity<Boolean> isBookmarked(@RequestParam Long blogId) {
        logger.info("Checking bookmark status for blog ID: {}", blogId);
        boolean result = bookmarkService.isBookmarked(blogId);
        logger.debug("Bookmark status for blog ID {}: {}", blogId, result);
        return ResponseEntity.ok(result);
    }

    /**
     * Toggles the bookmark status for the specified blog.
     *
     * @param blogId the ID of the blog to toggle
     * @return true if now bookmarked, false if removed
     */
    @PostMapping("/{blogId}/toggle")
    public ResponseEntity<Boolean> toggle(@PathVariable Long blogId) {
        logger.info("Toggling bookmark for blog ID: {}", blogId);
        boolean result = bookmarkService.toggle(blogId);
        logger.debug("Toggle result for blog ID {}: {}", blogId, result);
        return ResponseEntity.ok(result);
    }

    /**
     * Lists all bookmarks for the current user with pagination.
     *
     * @param page the page number (default 0)
     * @param size the page size (default 20)
     * @return paginated list of bookmarked blogs
     */
    @GetMapping
    public ResponseEntity<?> list(@RequestParam(defaultValue = "0") int page,
                                  @RequestParam(defaultValue = "20") int size) {
        logger.info("Listing bookmarks - page: {}, size: {}", page, size);
        return ResponseEntity.ok(bookmarkService.listMyBookmarks(PageRequest.of(page, size)));
    }
}
